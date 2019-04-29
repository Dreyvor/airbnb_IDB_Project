public class CtrlPredQuer {
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

    public void execBtn21(){
        Utils.execAndDisplayQuery(q21,null,"Query 2.1");
    }
    public void execBtn22(){
        Utils.execAndDisplayQuery(q22,null,"Query 2.2");
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
            " join house_details hd on p.listing_id = hd.listing_id and hd.bedrooms = 8.0";

    private String q22 = "select avg(rg.review_scores_cleanliness)" +
            " from amenitiesPL aPL" +
            " join amenitiesREL aREL on aREL.AMENITIES_ID = aPL.AMENITIES_ID and aPL.AMENITIES like 'TV'" +
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

    private String q31 = "Select * from listing where name like '%home%'";
    private String q32 = "Select * from listing where name like '%home%'";
    private String q33 = "Select * from listing where name like '%home%'";
    private String q34 = "Select * from listing where name like '%home%'";
    private String q35 = "Select * from listing where name like '%home%'";
    private String q36 = "Select * from listing where name like '%home%'";
    private String q37 = "Select * from listing where name like '%home%'";
    private String q38 = "Select * from listing where name like '%home%'";
    private String q39 = "Select * from listing where name like '%home%'";
    private String q310 = "Select * from listing where name like '%home%'";
    private String q311 = "Select * from listing where name like '%home%'";
    private String q312 = "Select * from listing where name like '%home%'";

}
