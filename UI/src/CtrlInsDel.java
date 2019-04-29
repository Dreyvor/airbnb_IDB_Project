public class CtrlInsDel {
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

    public void changeViewToInsertListing(){
        MyApplication.window.setScene(MyApplication.insertListing);
    }

    public void changeViewToInsertHost(){
        MyApplication.window.setScene(MyApplication.insertHost);
    }

    public void changeViewToInsertReview(){
        MyApplication.window.setScene(MyApplication.insertReview);
    }
}
