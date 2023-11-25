import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;
@Named
@SessionScoped
public class NavigationBean implements java.io.Serializable{
    public NavigationBean() {
    }

    public void redirectToAdminHome() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.redirect("admin_home.xhtml");
        facesContext.responseComplete();
    }
    public String goToProductPage() throws IOException {
        return "admin_products";
    }
    public String goToCalendarPage() throws IOException {
        return "admin_calendar";
    }
    public String goToHomePage() throws IOException {
        return "admin_home";
    }
    public String goToCasesPage() throws IOException {
        return "admin_cases";
    }
    public String goToMembers() throws IOException {
        return "admin_members";
    }


}
