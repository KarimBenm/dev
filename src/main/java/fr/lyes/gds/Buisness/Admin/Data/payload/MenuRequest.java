package fr.lyes.gds.Buisness.Admin.Data.payload;

import fr.lyes.gds.Buisness.Admin.Data.Entities.Menu;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class MenuRequest {

    @NotBlank
    private String urls;

    @NotBlank
    private Long id;

    @NotBlank
    private String label;

    @NotBlank
    private String icon;

    @NotBlank
    private boolean parents;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isParents() {
        return parents;
    }

    public void setParents(boolean parents) {
        this.parents = parents;
    }



    @NotBlank

    private Menu menu;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Menu> getSousMenuList() {
        return sousMenuList;
    }

    public void setsousMenuList(List<Menu> sousMenuList) {
        this.sousMenuList = sousMenuList;
    }

    @NotBlank
    private List<Menu> sousMenuList;

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }




    public MenuRequest() {

    }
}
