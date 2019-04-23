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

    String q21 = "Select name as Listing_name from listing where name like '%home%'";
    String q22 = "select avg(price) as average from price";
    String q23 = "Select * from listing where name like '%home%'";
    String q24 = "Select * from listing where name like '%home%'";
    String q25 = "Select * from listing where name like '%home%'";
    String q26 = "Select * from listing where name like '%home%'";
    String q27 = "Select * from listing where name like '%home%'";
    String q28 = "Select * from listing where name like '%home%'";
    String q29 = "Select * from listing where name like '%home%'";
    String q210 = "Select * from listing where name like '%home%'";

    String q31 = "Select * from listing where name like '%home%'";
    String q32 = "Select * from listing where name like '%home%'";
    String q33 = "Select * from listing where name like '%home%'";
    String q34 = "Select * from listing where name like '%home%'";
    String q35 = "Select * from listing where name like '%home%'";
    String q36 = "Select * from listing where name like '%home%'";
    String q37 = "Select * from listing where name like '%home%'";
    String q38 = "Select * from listing where name like '%home%'";
    String q39 = "Select * from listing where name like '%home%'";
    String q310 = "Select * from listing where name like '%home%'";
    String q311 = "Select * from listing where name like '%home%'";
    String q312 = "Select * from listing where name like '%home%'";

}
