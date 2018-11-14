package project.udacity.my.inventoryapp;

import java.util.List;

import project.udacity.my.inventoryapp.objects.EbayItem;

public interface Utility {

    boolean isNetworkAvailable();

    void printItems(List<EbayItem> items, String tag);
}
