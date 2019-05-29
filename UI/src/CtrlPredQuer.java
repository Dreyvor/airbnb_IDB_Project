import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.ResultSet;

public class CtrlPredQuer {
    private String nb_bedrooms = "8";
    private String amenity = "TV";

    @FXML
    private TextField q21_nb_bedrooms = new TextField();
    @FXML
    private ComboBox q22_amenities = new ComboBox();

    public void gotoViewMain() {
        CtrlMenu.changeView("Main");
    }
    public void gotoViewSearch() {
        CtrlMenu.changeView("Search");
    }
    public void gotoViewPredQuer() {
        CtrlMenu.changeView("Predefined queries");
    }
    public void gotoViewInsDel() {
        CtrlMenu.changeView("Insert/delete");
    }

    public void initialize(){
        ResultSet res = Utils.executeQuery("select AMENITIES from AMENITIESPL order by AMENITIES asc");
        try {
            while (res.next()) q22_amenities.getItems().add(res.getString(1));
        }catch(java.sql.SQLException e){
            e.printStackTrace();
        }
    }

    public void execBtn21(){
        String temp = q21_nb_bedrooms.getText();
        if (temp != null && temp.length() > 0 && Utils.isInt(temp)) nb_bedrooms=temp;
        else nb_bedrooms = "8";

        Utils.execAndDisplayQuery(q21 + nb_bedrooms,null,"Query 2.1");
    }
    public void execBtn22(){
        String temp = (String)q22_amenities.getValue();
        if(temp != null && temp.length() > 0) amenity = temp;
        else amenity = "TV";

        Utils.execAndDisplayQuery(q22_part1 + amenity + q22_part2,null,"Query 2.2");
    }
    public void execBtn23(){
        Utils.execAndDisplayQuery(q23,null,"Query 2.3");
    }
    public void execBtn24(){
        Utils.execAndDisplayQuery(q24,null,"Query 2.4");
    }
    public void execBtn25(){
        Utils.execAndDisplayQuery(q25,null,"Query 2.5");
    }
    public void execBtn26(){
        Utils.execAndDisplayQuery(q26,null,"Query 2.6");
    }
    public void execBtn27(){
        Utils.execAndDisplayQuery(q27,null,"Query 2.7");
    }
    public void execBtn28(){
        Utils.execAndDisplayQuery(q28,null,"Query 2.8");
    }
    public void execBtn29(){
        Utils.execAndDisplayQuery(q29,null,"Query 2.9");
    }
    public void execBtn210(){
        Utils.execAndDisplayQuery(q210,null,"Query 2.10");
    }

    public void execBtn31(){
        Utils.execAndDisplayQuery(q31,null,"Query 3.1");
    }
    public void execBtn32(){
        Utils.execAndDisplayQuery(q32,null,"Query 3.2");
        System.out.println(q31);
        System.out.println(q32);
        System.out.println(q33);
        System.out.println(q34);
        System.out.println(q35);
        System.out.println(q36);
        System.out.println(q37);
        System.out.println(q38);
        System.out.println(q39);
        System.out.println(q310);
        System.out.println(q311);
        System.out.println(q312);

    }
    public void execBtn33(){
        Utils.execAndDisplayQuery(q33,null,"Query 3.3");
    }
    public void execBtn34() {
        Utils.execAndDisplayQuery(q34, null,"Query 3.4");
    }
    public void execBtn35(){
        Utils.execAndDisplayQuery(q35,null,"Query 3.5");
    }
    public void execBtn36(){
        Utils.execAndDisplayQuery(q36,null,"Query 3.6");
    }
    public void execBtn37(){
        Utils.execAndDisplayQuery(q37,null,"Query 3.7");
    }
    public void execBtn38(){
        Utils.execAndDisplayQuery(q38,null,"Query 3.8");
    }
    public void execBtn39(){
        Utils.execAndDisplayQuery(q39,null,"Query 3.9");
    }
    public void execBtn310(){
        Utils.execAndDisplayQuery(q310,null,"Query 3.10");
    }
    public void execBtn311(){
        Utils.execAndDisplayQuery(q311,null,"Query 3.11");
    }
    public void execBtn312(){
        Utils.execAndDisplayQuery(q312,null,"Query 3.12");
    }

    //#########################################
    //Begin interesting things

    private String q21 = "select avg(price) as Average_price" +
            " from price p" +
            " join house_details hd on p.listing_id = hd.listing_id and hd.bedrooms = ";

    private String q22_part1= "select avg(rg.review_scores_cleanliness)" +
            " from amenitiesPL aPL" +
            " join amenitiesREL aREL on aREL.AMENITIES_ID = aPL.AMENITIES_ID and aPL.AMENITIES like '";
    private String q22_part2 = "'" +
            " join house_details hds on hds.LISTING_ID = aREL.LISTING_ID" +
            " join listing l on l.LISTING_ID = hds.LISTING_ID" +
            " join review_grades rg on rg.LISTING_ID = l.LISTING_ID";

    private String q23 = "select h.host_id, au.user_name" +
            " from host h join all_users au on au.user_id = h.host_id" +
            " where h.host_id in" +
            " (select distinct l.host_id from listing l join calendar ca on l.listing_id = ca.listing_id" +
            " and ca.available = 't' and ca.\"date\" >= date '2019-03-01'" +
            " and ca.\"date\" <= date '2019-09-01')";

    private String q24 = "select COUNT(*)" +
            " from LISTING L" +
            " join ALL_USERS AU on L.HOST_ID = AU.USER_ID and AU.USER_ID IN" +
            " (Select AU1.USER_ID" +
            " from ALL_USERS AU1" +
            " join ALL_USERS AU2 on AU1.USER_NAME = AU2.USER_NAME and AU1.USER_ID != AU2.USER_ID)";

    private String q25 = "Select distinct ca.\"date\"" +
            " from calendar ca" +
            " join listing l on l.LISTING_ID = ca.LISTING_ID and ca.AVAILABLE = 't'" +
            " join all_users au on au.USER_ID = l.HOST_ID  and au.USER_NAME like 'Viajes Eco'";

    private String q26 = "select *" +
            " from all_users au" +
            " where au.USER_ID in" +
            " (select l.host_id from listing l GROUP BY l.HOST_ID having count(*) = 1)";

    private String q27 = "select avg(p1.price) - avg(p2.price) as \"Difference withWifi-noWifi\"" +
            " from price p1, price p2" +
            " where p1.LISTING_ID in" +
            " (select aREL.listing_id" +
            " from amenitiesPL aPL join amenitiesREL aREL on aREL.AMENITIES_ID = aPL.AMENITIES_ID and aPL.AMENITIES = 'Wifi')" +
            " and p2.LISTING_ID not in" +
            " (select aREL.listing_id" +
            " from amenitiesPL aPL join amenitiesREL aREL on aREL.AMENITIES_ID = aPL.AMENITIES_ID and aPL.AMENITIES = 'Wifi')";

    private String q28 = "select avg(p1.price) - avg(p2.price) as \"Difference Berlin-Madrid\"" +
            " from price p1 join price p2 on not p2.LISTING_ID = p1.LISTING_ID" +
            " where p1.LISTING_ID in" +
            " (select hds.listing_id" +
            " from cityPL cPL join neighbourhoodPL nPL on nPL.CITY_ID = cPL.CITY_ID and cPL.CITY = 'Berlin'" +
            " join listing l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID" +
            " join house_details hds on l.LISTING_ID = hds.LISTING_ID and hds.BEDROOMS = 8)" +
            " and p2.LISTING_ID in" +
            " (select hds.listing_id" +
            " from cityPL cPL join neighbourhoodPL nPL on nPL.CITY_ID = cPL.CITY_ID and cPL.CITY = 'Madrid'" +
            " join listing l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID" +
            " join house_details hds on l.LISTING_ID = hds.LISTING_ID and hds.BEDROOMS = 8)";

    private String q29 = "select au.user_id, au.user_name, count(l.HOST_ID) as listing_counter" +
            " from cityPL cPL" +
            " join neighbourhoodPL nPL on nPL.CITY_ID = cPL.CITY_ID and cPL.COUNTRY_CODE = 'ES'" +
            " join listing l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID" +
            " join all_users au on l.HOST_ID = au.user_id" +
            " group by au.user_id, au.user_name" +
            " order by listing_counter desc fetch first 10 rows only";

    private String q210 = "select l.listing_id, l.name" +
            " from cityPL cPL" +
            " join neighbourhoodPL nPL on nPL.CITY_ID = cPL.CITY_ID and cPL.CITY = 'Barcelona'" +
            " join listing l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID" +
            " join review_grades rg on rg.LISTING_ID = l.LISTING_ID and rg.REVIEW_SCORES_RATING is not null" +
            " order by rg.REVIEW_SCORES_RATING desc fetch first 10 rows only";
// #####################################################################################################################
    private String q31 = "Select cPL.city, count(*)" +
            " from cityPL cPL" +
            " join neighbourhoodPL nPL on nPL.CITY_ID = cPL.CITY_ID" +
            " join listing l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID" +
            " join house_details hds on l.LISTING_ID = hds.LISTING_ID and hds.SQUARE_FEET is not null and hds.SQUARE_FEET != 0" +
            " group by cPL.CITY" +
            " order by cPL.CITY asc";

    private String q32 = "select neighbourhood_name, median_score" +
            " from (" +
            " select nPL.neighbourhood as neighbourhood_name," +
            " rg.REVIEW_SCORES_RATING as median_score," +
            " ROW_NUMBER() OVER (PARTITION BY nPL.NEIGHBOURHOOD_ID ORDER BY rg.REVIEW_SCORES_RATING) as rn," +
            " count(*) over(PARTITION BY nPL.NEIGHBOURHOOD_ID) as cnt" +
            " from cityPL cPL" +
            " join neighbourhoodPL nPL on nPL.CITY_ID = cPL.CITY_ID and cPL.CITY = 'Madrid'" +
            " join listing l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID" +
            " join review_grades rg on rg.LISTING_ID = l.LISTING_ID and rg.REVIEW_SCORES_RATING is not null" +
            " )" +
            " where rn = floor((cnt+1)/2)" +
            " order by median_score DESC" +
            " FETCH NEXT 5 ROWS ONLY";

    private String q33 = "select *" +
            " from all_users au3" +
            " where au3.USER_ID in(" +
            " select ids" +
            " from(" +
            " select au.USER_ID as ids, count(*) as nb_listing" +
            " from all_users au" +
            " join listing l on l.HOST_ID = au.USER_ID" +
            " group by au.USER_ID" +
            " having count(*) = (" +
            " select nb_listing2 from(" +
            " select au2.USER_ID, count(*) as nb_listing2" +
            " from all_users au2" +
            " join listing l2 on l2.HOST_ID = au2.USER_ID" +
            " group by au2.USER_ID" +
            " order by nb_listing2 desc" +
            " fetch first row only" +
            " )" +
            " )" +
            " )" +
            " )";

    private String q34 = "select l.LISTING_ID, l.name, avg(cal.PRICE) as mean_price" +
            " from cityPL cPL" +
            " join NEIGHBOURHOODPL nPL on nPL.CITY_ID = cPL.CITY_ID" +
            " and cPL.CITY ='Berlin'" +
            " join LISTING l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID" +
            " join HOUSE_DETAILS hds on l.LISTING_ID = hds.LISTING_ID" +
            " and hds.BEDS > 1" +
            " join PROPERTY_TYPEPL propPL on propPL.PROPERTY_TYPE_ID = hds.PROPERTY_TYPE" +
            " and propPL.PROPERTY_TYPE = 'Apartment'" +
            " join REVIEW_GRADES rg on rg.LISTING_ID = l.LISTING_ID" +
            " and rg.REVIEW_SCORES_LOCATION >= 8" +
            " join CONTRACT_POLICIES conPOL on l.LISTING_ID = conPOL.LISTING_ID" +
            " join CANCELLATION_POLICYPL canPL on canPL.CANCELLATION_POLICY_ID = conPOL.CANCELLATION_POLICY" +
            " and canPL.CANCELLATION_POLICY = 'flexible'" +
            " join HOST_VERIFICATIONSREL hvREL on l.HOST_ID = hvREL.HOST_ID" +
            " join HOST_VERIFICATIONSPL hvPL on hvREL.HOST_VERIFICATION_ID = hvPL.HOST_VERIFICATIONS_ID" +
            " and hvPL.HOST_VERIFICATIONS = 'government_id'" +
            " join calendar cal on l.LISTING_ID = cal.LISTING_ID" +
            " and cal.\"date\" >= TO_DATE('01-03-2019','DD-MM-YYYY')" +
            " and cal.\"date\" <= TO_DATE('30-04-2019','DD-MM-YYYY')" +
            " and cal.AVAILABLE = 't'" +
            " group by l.LISTING_ID, l.name" +
            " order by mean_price asc" +
            " fetch first 5 rows only";

    private String q35 = "select acc, lid, l.NAME, score" +
            " from (" +
            " select hds.ACCOMMODATES as acc, hds.LISTING_ID as lid, rg.REVIEW_SCORES_RATING as score," +
            " ROW_NUMBER() OVER (PARTITION BY hds.ACCOMMODATES ORDER BY rg.REVIEW_SCORES_RATING desc) as rn" +
            " from house_details hds" +
            " join review_grades rg on rg.LISTING_ID = hds.LISTING_ID and rg.REVIEW_SCORES_RATING is not null" +
            " where hds.LISTING_ID in(" +
            " select ids from(" +
            " select aREL.LISTING_ID as ids, count(*) as nb_amen" +
            " from amenitiesPL aPL" +
            " join amenitiesREL aREL on aREL.AMENITIES_ID = aPL.AMENITIES_ID" +
            " and (aPL.AMENITIES = 'TV' OR aPL.AMENITIES = 'Wifi' OR aPL.AMENITIES = 'Internet' OR aPL.AMENITIES = 'Free street parking')" +
            " group by aREL.LISTING_ID" +
            " having count(*) > 1" +
            " )" +
            " )" +
            " )" +
            " join listing l on l.LISTING_ID = lid" +
            " where rn <= 5";

    private String q36 = "select hid, au.USER_NAME, lid, popularity" +
            " from (" +
            " select l.HOST_ID as hid," +
            " l.LISTING_ID as lid," +
            " count(rc.REVIEW_ID) as popularity," +
            " row_number() over (partition by l.HOST_ID order by count(rc.REVIEW_ID) desc) as rn" +
            " from listing l" +
            " join review_comments rc on rc.LISTING_ID = l.LISTING_ID" +
            " group by l.HOST_ID, l.LISTING_ID" +
            " )" +
            " join all_users au on hid = au.USER_ID" +
            " where rn <= 3";

    private String q37 = "select neighbourhood, amenity, score_amenity" +
            " from (" +
            " select nPL.NEIGHBOURHOOD as neighbourhood," +
            " aPL.AMENITIES as amenity," +
            " count(aREL.AMENITIES_ID) as score_amenity," +
            " row_number() over (partition by nPL.neighbourhood order by count(aREL.AMENITIES_ID) desc) as rn" +
            " from cityPL cPL" +
            " join neighbourhoodPL nPL on nPL.CITY_ID = cPL.CITY_ID and cPL.CITY = 'Berlin'" +
            " join listing l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID" +
            " join house_details hds on l.LISTING_ID = hds.LISTING_ID" +
            " join room_typePL rtPL on hds.ROOM_TYPE = rtPL.ROOM_TYPE_ID and rtPL.ROOM_TYPE ='Private room'" +
            " join amenitiesREL aREL on hds.LISTING_ID = aREL.LISTING_ID" +
            " join amenitiesPL aPL on aREL.AMENITIES_ID = aPL.AMENITIES_ID" +
            " group by nPL.NEIGHBOURHOOD, aPL.AMENITIES" +
            " )" +
            " where rn <= 3";


    private String q38 = "select (" +
            " (" +
            " select avg(rg1.REVIEW_SCORES_COMMUNICATION)" +
            " from LISTING l" +
            " join REVIEW_GRADES rg1 on rg1.LISTING_ID = l.LISTING_ID and l.HOST_ID = (" +
            " select host_id_max" +
            " from (" +
            " select hvREL.HOST_ID as host_id_max, count(*) as nb_verif" +
            " from HOST_VERIFICATIONSREL hvREL" +
            " group by hvREL.HOST_ID" +
            " order by count(*) desc" +
            " fetch first row only" +
            " )" +
            " join listing l on l.HOST_ID = host_id_max" +
            " join review_grades rg on rg.LISTING_ID = l.LISTING_ID and rg.REVIEW_SCORES_COMMUNICATION is not null" +
            " fetch first 1 rows only" +
            " )" +
            " group by DUAL.DUMMY" +
            " )" +
            " -" +
            " (" +
            " select avg(rg1.REVIEW_SCORES_COMMUNICATION)" +
            " from LISTING l" +
            " join REVIEW_GRADES rg1 on rg1.LISTING_ID = l.LISTING_ID and l.HOST_ID = (" +
            " select l.HOST_ID as host_id_min" +
            " from listing l" +
            " join review_grades rg on rg.LISTING_ID = l.LISTING_ID and rg.REVIEW_SCORES_COMMUNICATION is not null" +
            " where l.HOST_ID in (" +
            " select distinct l.HOST_ID" +
            " from listing l" +
            " left join HOST_VERIFICATIONSREL hvREL on l.HOST_ID = hvREL.HOST_ID" +
            " where hvREL.HOST_ID is null" +
            " )" +
            " fetch first row only" +
            " )" +
            " group by DUAL.DUMMY" +
            " )" +
            " ) as \"difference asked\"" +
            " from DUAL"; //TODO : Change this query

    private String q39 = "select cPL.CITY, sum(nb_review) as nb_review_2" +
            " from cityPL cPL" +
            " join NEIGHBOURHOODPL nPL on nPL.CITY_ID = cPL.CITY_ID" +
            " join LISTING l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID" +
            " join (" +
            " select hds.LISTING_ID ids," +
            " count(*) as nb_review" +
            " from house_details hds" +
            " join review_comments rc on rc.LISTING_ID = hds.LISTING_ID" +
            " where hds.ROOM_TYPE in (" +
            " select r_type" +
            " from (" +
            " select hds.room_type as r_type" +
            " from house_details hds" +
            " group by hds.ROOM_TYPE" +
            " having avg(hds.ACCOMMODATES) > 3" +
            " )" +
            " )" +
            " group by hds.LISTING_ID" +
            " ) on l.LISTING_ID = ids" +
            " group by cPL.CITY" +
            " order by nb_review_2 desc" +
            " fetch first row only";

    private String q310 = "select nPL.NEIGHBOURHOOD, counter / count(distinct l2.LISTING_ID) * 100 as \"occupancy percentage [%]\"" +
            " from cityPL cPL" +
            " join NEIGHBOURHOODPL nPL on nPL.CITY_ID = cPL.CITY_ID and cPL.CITY = 'Madrid'" +
            " join(" +
            " select l.NEIGHBOURHOOD_ID nid," +
            " count(distinct l.LISTING_ID) as counter" +
            " from host h" +
            " join LISTING l on l.host_id = h.host_id and h.HOST_SINCE <= TO_DATE('01-06-2017','DD-MM-YYYY')" +
            " join CALENDAR cal on l.LISTING_ID = cal.LISTING_ID" +
            " and cal.AVAILABLE = 'f'" +
            " and cal.\"date\" >= TO_DATE('01-01-2019','DD-MM-YYYY')" +
            " and cal.\"date\" < TO_DATE('01-01-2020','DD-MM-YYYY')" +
            " group by l.NEIGHBOURHOOD_ID" +
            " ) on nid = nPL.NEIGHBOURHOOD_ID" +
            " join listing l2 on nPL.NEIGHBOURHOOD_ID = l2.NEIGHBOURHOOD_ID" +
            " group by nPL.NEIGHBOURHOOD, counter" +
            " having counter / count(distinct l2.LISTING_ID) >= 0.5";

    private String q311 = "select coPL.COUNTRY_NAME, count_filter.counter / count_total.counter * 100 as \"occupancy percentage [%]\"" +
            " from country coPL" +
            " join (" +
            " select coPL.COUNTRY_CODE as co_code, count(distinct l.listing_id) as counter" +
            " from country coPL" +
            " join CITYPL ciPL on coPL.COUNTRY_CODE = ciPL.COUNTRY_CODE" +
            " join NEIGHBOURHOODPL nPL on nPL.CITY_ID = ciPL.CITY_ID" +
            " join listing l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID" +
            " join CALENDAR cal on l.LISTING_ID = cal.LISTING_ID" +
            " and cal.\"date\" >= TO_DATE('01-01-2018','DD-MM-YYYY')" +
            " and cal.\"date\" < TO_DATE('01-01-2019','DD-MM-YYYY')" +
            " and cal.AVAILABLE = 't'" +
            " group by coPL.COUNTRY_CODE" +
            " ) count_filter on coPL.COUNTRY_CODE = count_filter.co_Code" +
            " join (" +
            " select coPL.country_code as co_Code, count(distinct l.listing_id) as counter" +
            " from country coPL" +
            " join cityPL cPL on cPL.country_code = coPL.COUNTRY_CODE" +
            " join NEIGHBOURHOODPL nPL on nPL.CITY_ID = cPL.CITY_ID" +
            " join LISTING l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID" +
            " join CALENDAR cal on l.LISTING_ID = cal.LISTING_ID" +
            " and cal.\"date\" >= TO_DATE('01-01-2018','DD-MM-YYYY')" +
            " and cal.\"date\" < TO_DATE('01-01-2019','DD-MM-YYYY')" +
            " group by coPL.COUNTRY_CODE" +
            " ) count_total on count_total.co_Code = count_filter.co_Code" +
            " where count_filter.counter / count_total.counter >= 0.2"; //TODO : Change this query

    private String q312 = "select count_filter.n as neighbourhood, count_filter.counter / count_total.counter * 100 as \"percentage of listing with strict with grace period\"" +
            " from (" +
            " select nPL.NEIGHBOURHOOD as n, count(l.LISTING_ID) as counter" +
            " from cityPL cPL" +
            " join NEIGHBOURHOODPL nPL on nPL.CITY_ID = cPL.CITY_ID and cPL.CITY = 'Barcelona'" +
            " join listing l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID" +
            " join CONTRACT_POLICIES conPOL on l.LISTING_ID = conPOL.LISTING_ID" +
            " join CANCELLATION_POLICYPL canPOL on canPOL.CANCELLATION_POLICY_ID = conPOL.CANCELLATION_POLICY and canPOL.CANCELLATION_POLICY = 'strict_14_with_grace_period'" +
            " group by nPL.NEIGHBOURHOOD" +
            " ) count_filter" +
            " join (" +
            " select nPL.neighbourhood as n, count(l.listing_id) as counter" +
            " from cityPL cPL" +
            " join NEIGHBOURHOODPL nPL on nPL.CITY_ID = cPL.CITY_ID and cPL.CITY = 'Barcelona'" +
            " join listing l on nPL.NEIGHBOURHOOD_ID = l.NEIGHBOURHOOD_ID" +
            " group by nPL.NEIGHBOURHOOD" +
            " ) count_total on count_total.n = count_filter.n" +
            " where count_filter.counter / count_total.counter > 0.05";

}
