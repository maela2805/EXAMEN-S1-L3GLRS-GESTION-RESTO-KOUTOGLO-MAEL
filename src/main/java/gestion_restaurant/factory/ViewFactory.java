package gestion_restaurant.factory;

import gestion_restaurant.view.*;

public class ViewFactory {

    private final ServiceFactory service = new ServiceFactory();
    public ComplementView complementView() { return new ComplementView(service.complement()); }
    public BurgerView burgerView() { return new BurgerView(service.burger()); }
    public MenuView menuView() { return new MenuView(service.menu()); }
    public LivreurView livreurView() { return new LivreurView(service.livreur()); }
    public MyMenu zoneQuartierMenu() { return new MyMenu(service.zone(), service.quartier()); }
}
