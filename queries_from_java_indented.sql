---------------------------------------1
Select cPL.city, count(*) from cityPL cPL 
join neighbourhoodPL nPL on nPL.CITY_ID = cPL.CITY_ID 
join listing l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID 
join house_details hds on l.LISTING_ID = hds.LISTING_ID and hds.SQUARE_FEET is not null 
group by cPL.CITY 
order by cPL.CITY asc;

---------------------------------------2
select neighbourhood_name, median_score 
from 
  ( select nPL.neighbourhood as neighbourhood_name, 
            rg.REVIEW_SCORES_RATING as median_score, 
            ROW_NUMBER() OVER (PARTITION BY nPL.NEIGHBOURHOOD_ID ORDER BY rg.REVIEW_SCORES_RATING) as rn, 
            count(*) over(PARTITION BY nPL.NEIGHBOURHOOD_ID) as cnt 
    from cityPL cPL 
    join neighbourhoodPL nPL on nPL.CITY_ID = cPL.CITY_ID and cPL.CITY = 'Madrid' 
    join listing l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID 
    join review_grades rg on rg.LISTING_ID = l.LISTING_ID and rg.REVIEW_SCORES_RATING is not null ) 
where rn = floor((cnt+1)/2) 
order by median_score DESC 
FETCH NEXT 5 ROWS ONLY;

---------------------------------------3
select * from all_users au3 
where au3.USER_ID in
  ( select ids 
    from 
      ( select au.USER_ID as ids, count(*) as nb_listing 
        from all_users au 
        join listing l on l.HOST_ID = au.USER_ID 
        group by au.USER_ID 
        having count(*) = 
          ( select nb_listing2 
            from
              ( select au2.USER_ID, count(*) as nb_listing2 
                from all_users au2 
                join listing l2 on l2.HOST_ID = au2.USER_ID 
                group by au2.USER_ID 
                order by nb_listing2 desc 
                fetch first row only 
              ) 
          ) 
      ) 
  )
;

---------------------------------------4
select l.LISTING_ID, l.name, avg(cal.PRICE) as mean_price 
from cityPL cPL 
join NEIGHBOURHOODPL nPL on nPL.CITY_ID = cPL.CITY_ID and cPL.CITY ='Berlin' 
join LISTING l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID 
join HOUSE_DETAILS hds on l.LISTING_ID = hds.LISTING_ID and hds.BEDS > 1 
join PROPERTY_TYPEPL propPL on propPL.PROPERTY_TYPE_ID = hds.PROPERTY_TYPE and propPL.PROPERTY_TYPE = 'Apartment' 
join REVIEW_GRADES rg on rg.LISTING_ID = l.LISTING_ID and rg.REVIEW_SCORES_LOCATION >= 8 
join CONTRACT_POLICIES conPOL on l.LISTING_ID = conPOL.LISTING_ID 
join CANCELLATION_POLICYPL canPL on canPL.CANCELLATION_POLICY_ID = conPOL.CANCELLATION_POLICY and canPL.CANCELLATION_POLICY = 'flexible' 
join HOST_VERIFICATIONSREL hvREL on l.HOST_ID = hvREL.HOST_ID 
join HOST_VERIFICATIONSPL hvPL on hvREL.HOST_VERIFICATION_ID = hvPL.HOST_VERIFICATIONS_ID and hvPL.HOST_VERIFICATIONS = 'government_id' 
join calendar cal on l.LISTING_ID = cal.LISTING_ID and cal."date" >= TO_DATE('01-03-2019','DD-MM-YYYY') and cal."date" <= TO_DATE('30-04-2019','DD-MM-YYYY') and cal.AVAILABLE = 't' 
group by l.LISTING_ID, l.name 
order by mean_price asc 
fetch first 5 rows only;

---------------------------------------5
select acc, lid, l.NAME, score 
from 
  ( select hds.ACCOMMODATES as acc, hds.LISTING_ID as lid, rg.REVIEW_SCORES_RATING as score, ROW_NUMBER() OVER (PARTITION BY hds.ACCOMMODATES ORDER BY rg.REVIEW_SCORES_RATING desc) as rn 
    from house_details hds 
    join review_grades rg on rg.LISTING_ID = hds.LISTING_ID and rg.REVIEW_SCORES_RATING is not null 
    where hds.LISTING_ID in
      ( select ids 
        from  
          ( select aREL.LISTING_ID as ids, count(*) as nb_amen 
            from amenitiesPL aPL 
            join amenitiesREL aREL on aREL.AMENITIES_ID = aPL.AMENITIES_ID and (aPL.AMENITIES = 'TV' OR aPL.AMENITIES = 'Wifi' OR aPL.AMENITIES = 'Internet' OR aPL.AMENITIES = 'Free street parking') 
            group by aREL.LISTING_ID 
            having count(*) > 1 
          ) 
      ) 
  ) 
join listing l on l.LISTING_ID = lid 
where rn <= 5;

---------------------------------------6
create index new_index_rc_listingid on REVIEW_COMMENTS (LISTING_ID);

select hid, au.USER_NAME, lid, popularity 
from 
  ( select l.HOST_ID as hid, 
           l.LISTING_ID as lid, 
           count(rc.REVIEW_ID) as popularity, 
           row_number() over (partition by l.HOST_ID order by count(rc.REVIEW_ID) desc) as rn 
    from listing l 
    join review_comments rc on rc.LISTING_ID = l.LISTING_ID 
    group by l.HOST_ID, l.LISTING_ID 
  ) 
join all_users au on hid = au.USER_ID 
where rn <= 3;

---------------------------------------7
select neighbourhood, amenity, score_amenity 
from 
  ( select nPL.NEIGHBOURHOOD as neighbourhood, 
           aPL.AMENITIES as amenity, 
           count(aREL.AMENITIES_ID) as score_amenity, 
           row_number() over (partition by nPL.neighbourhood order by count(aREL.AMENITIES_ID) desc) as rn 
    from cityPL cPL 
    join neighbourhoodPL nPL on nPL.CITY_ID = cPL.CITY_ID and cPL.CITY = 'Berlin' 
    join listing l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID 
    join house_details hds on l.LISTING_ID = hds.LISTING_ID 
    join room_typePL rtPL on hds.ROOM_TYPE = rtPL.ROOM_TYPE_ID and rtPL.ROOM_TYPE ='Private room' 
    join amenitiesREL aREL on hds.LISTING_ID = aREL.LISTING_ID 
    join amenitiesPL aPL on aREL.AMENITIES_ID = aPL.AMENITIES_ID 
    group by nPL.NEIGHBOURHOOD, aPL.AMENITIES 
  ) 
where rn <= 3;

---------------------------------------8
select ( 
  ( select avg(rg1.REVIEW_SCORES_COMMUNICATION) 
    from LISTING l 
    join REVIEW_GRADES rg1 on rg1.LISTING_ID = l.LISTING_ID and l.HOST_ID = 
      ( select host_id_max 
        from ( 
          select hvREL.HOST_ID as host_id_max, count(*) as nb_verif 
          from HOST_VERIFICATIONSREL hvREL 
          group by hvREL.HOST_ID 
          order by count(*) desc 
          fetch first row only ) 
        join listing l on l.HOST_ID = host_id_max 
        join review_grades rg on rg.LISTING_ID = l.LISTING_ID and rg.REVIEW_SCORES_COMMUNICATION is not null 
        fetch first 1 rows only
      )
    group by DUAL.DUMMY 
  ) 
  - 
  ( select avg(rg1.REVIEW_SCORES_COMMUNICATION) 
    from LISTING l 
    join REVIEW_GRADES rg1 on rg1.LISTING_ID = l.LISTING_ID and l.HOST_ID = 
      ( select l.HOST_ID as host_id_min 
        from listing l 
        join review_grades rg on rg.LISTING_ID = l.LISTING_ID and rg.REVIEW_SCORES_COMMUNICATION is not null 
        where l.HOST_ID in ( 
          select distinct l.HOST_ID 
          from listing l 
          left join HOST_VERIFICATIONSREL hvREL on l.HOST_ID = hvREL.HOST_ID 
          where hvREL.HOST_ID is null ) 
        fetch first row only 
      ) 
    group by DUAL.DUMMY 
  ) 
) as "difference asked" from DUAL;

---------------------------------------9
create index new_index_l_neigh on LISTING (NEIGHBOURHOOD_ID);

select cPL.CITY, sum(nb_review) as nb_review_2 
from cityPL cPL 
join NEIGHBOURHOODPL nPL on nPL.CITY_ID = cPL.CITY_ID 
join LISTING l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID 
join 
  ( select hds.LISTING_ID ids, count(*) as nb_review from house_details hds 
    join review_comments rc on rc.LISTING_ID = hds.LISTING_ID 
    where hds.ROOM_TYPE in ( 
      select r_type 
      from 
        ( select hds.room_type as r_type 
          from house_details hds 
          group by hds.ROOM_TYPE 
          having avg(hds.ACCOMMODATES) > 3 
        ) 
      ) 
    group by hds.LISTING_ID )
    on l.LISTING_ID = ids 
group by cPL.CITY 
order by nb_review_2 desc 
fetch first row only;

---------------------------------------10
create index new_index_cal_date on CALENDAR ("date");
--create index new_index_cal_date on CALENDAR (TRUNC("date"));
--drop index new_index_cal_date;
create index new_index_host_since on HOST (HOST_SINCE);

select nPL.NEIGHBOURHOOD, counter / count(distinct l2.LISTING_ID) * 100 as "occupancy percentage [%]" 
from cityPL cPL 
join NEIGHBOURHOODPL nPL on nPL.CITY_ID = cPL.CITY_ID and cPL.CITY = 'Madrid' 
join
  ( select l.NEIGHBOURHOOD_ID nid, count(distinct l.LISTING_ID) as counter 
    from host h 
    join LISTING l on l.host_id = h.host_id and h.HOST_SINCE <= TO_DATE('01-06-2017','DD-MM-YYYY') 
    join CALENDAR cal on l.LISTING_ID = cal.LISTING_ID and cal.AVAILABLE = 'f' and cal."date" >= TO_DATE('01-01-2019','DD-MM-YYYY') and cal."date" < TO_DATE('01-01-2020','DD-MM-YYYY') 
    group by l.NEIGHBOURHOOD_ID 
  ) 
  on nid = nPL.NEIGHBOURHOOD_ID 
join listing l2 on nPL.NEIGHBOURHOOD_ID = l2.NEIGHBOURHOOD_ID 
group by nPL.NEIGHBOURHOOD, counter having counter / count(distinct l2.LISTING_ID) >= 0.5;

---------------------------------------11
create index new_index_cal_listingid on CALENDAR (LISTING_ID);

select coPL.COUNTRY_NAME, count_filter.counter / count_total.counter * 100 as "occupancy percentage [%]" 
from country coPL 
join 
  ( select coPL.COUNTRY_CODE as co_code, count(distinct l.listing_id) as counter 
    from country coPL 
    join CITYPL ciPL on coPL.COUNTRY_CODE = ciPL.COUNTRY_CODE 
    join NEIGHBOURHOODPL nPL on nPL.CITY_ID = ciPL.CITY_ID 
    join listing l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID 
    join CALENDAR cal on l.LISTING_ID = cal.LISTING_ID and cal."date" >= TO_DATE('01-01-2018','DD-MM-YYYY') and cal."date" < TO_DATE('01-01-2019','DD-MM-YYYY') and cal.AVAILABLE = 't' 
    group by coPL.COUNTRY_CODE 
  ) count_filter 
  on coPL.COUNTRY_CODE = count_filter.co_Code 
join 
  ( select coPL.country_code as co_Code, count(distinct l.listing_id) as counter 
    from country coPL 
    join cityPL cPL on cPL.country_code = coPL.COUNTRY_CODE 
    join NEIGHBOURHOODPL nPL on nPL.CITY_ID = cPL.CITY_ID 
    join LISTING l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID 
    join CALENDAR cal on l.LISTING_ID = cal.LISTING_ID and cal."date" >= TO_DATE('01-01-2018','DD-MM-YYYY') and cal."date" < TO_DATE('01-01-2019','DD-MM-YYYY')
    group by coPL.COUNTRY_CODE 
  ) count_total 
  on count_total.co_Code = count_filter.co_Code 
where count_filter.counter / count_total.counter >= 0.2;

---------------------------------------12
select count_filter.n as neighbourhood, count_filter.counter / count_total.counter * 100 as "percentage of listing with strict with grace period" 
from 
  ( select nPL.NEIGHBOURHOOD as n, count(l.LISTING_ID) as counter 
    from cityPL cPL join NEIGHBOURHOODPL nPL on nPL.CITY_ID = cPL.CITY_ID and cPL.CITY = 'Barcelona' 
    join listing l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID 
    join CONTRACT_POLICIES conPOL on l.LISTING_ID = conPOL.LISTING_ID 
    join CANCELLATION_POLICYPL canPOL on canPOL.CANCELLATION_POLICY_ID = conPOL.CANCELLATION_POLICY and canPOL.CANCELLATION_POLICY = 'strict_14_with_grace_period' 
    group by nPL.NEIGHBOURHOOD 
  ) count_filter 
join 
  ( select nPL.neighbourhood as n, count(l.listing_id) as counter 
    from cityPL cPL 
    join NEIGHBOURHOODPL nPL on nPL.CITY_ID = cPL.CITY_ID and cPL.CITY = 'Barcelona' 
    join listing l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID 
    group by nPL.NEIGHBOURHOOD 
  ) count_total 
  on count_total.n = count_filter.n 
where count_filter.counter / count_total.counter > 0.05;
