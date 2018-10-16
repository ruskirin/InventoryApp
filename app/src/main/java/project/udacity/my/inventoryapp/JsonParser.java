package project.udacity.my.inventoryapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/***
 * Contains:
 *  getEbayData - base JSON parser of a given query
 *  buildUri - build the URI for desired items
 */
public class JsonParser {

    public static void getEbayData(String jsonResponse, List<EbayItem> items) {
        final int ITEM_DEF = 0;
        final int THNAIL_SMALL = 2;
        final String MAIN_ARRAY = "findItemsByCategoryResponse";
        final String QUERY_RESPONSE = "ack";
        final String QUERY_SEARCH = "searchResult";
        final String QUERY_SEARCH_ITEM = "item";
        final String SEARCH_ITEM_TITLE = "title";
        final String SEARCH_ITEM_STATUS = "sellingStatus";
        final String SEARCH_ITEM_STATUS_PRICE = "currentPrice";
        final String SEARCH_ITEM_STATUS_PRICE_VALUE = "__value__";
        final String SEARCH_ITEM_SELLER = "sellerInfo";
        final String SEARCH_ITEM_SELLER_NAME = "sellerUserName";
        final String SEARCH_ITEM_STORE = "storeInfo";
        final String SEARCH_ITEM_STORE_CONTACT = "storeURL";
        final String SEARCH_ITEM_CONTAINER = "galleryInfoContainer";
        final String SEARCH_ITEM_CONTAINER_THNAIL = "galleryURL";
        final String SEARCH_ITEM_CONTAINER_THNAIL_VALUE = "__value__";

        /***
         * For some reason the JSON response from this api wraps EVERYTHING in array brackets,
         *  to avoid instantiating all of the go-between objects I chain a lot of the calls
         */
        try {
            EbayItem item = new EbayItem();
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject categoryObject = jsonObject.getJSONArray(MAIN_ARRAY).getJSONObject(ITEM_DEF);
            JSONArray code = categoryObject.getJSONArray(QUERY_RESPONSE);

            if(code.getJSONObject(ITEM_DEF).getString(QUERY_RESPONSE).equals("Success")) {
                JSONArray itemArray = categoryObject.getJSONArray(QUERY_SEARCH)
                        .getJSONObject(ITEM_DEF)
                        .getJSONArray(QUERY_SEARCH_ITEM);

                for (int i = 0; i < itemArray.length(); i++) {
                    JSONObject jsonItem = itemArray.getJSONObject(i);
                    item.setName(jsonItem.getJSONArray(SEARCH_ITEM_TITLE)
                            .getString(ITEM_DEF));
                    item.setPrice(jsonItem.getJSONArray(SEARCH_ITEM_STATUS)
                            .getJSONObject(ITEM_DEF)
                            .getJSONArray(SEARCH_ITEM_STATUS_PRICE)
                            .getJSONObject(ITEM_DEF)
                            .getDouble(SEARCH_ITEM_STATUS_PRICE_VALUE));
                    item.setSellerName(jsonItem.getJSONArray(SEARCH_ITEM_SELLER)
                            .getJSONObject(ITEM_DEF)
                            .getJSONArray(SEARCH_ITEM_SELLER_NAME)
                            .getString(ITEM_DEF));
                    if(jsonItem.has(SEARCH_ITEM_STORE)) {
                        item.setSellerContact(jsonItem.getJSONArray(SEARCH_ITEM_STORE)
                                .getJSONObject(ITEM_DEF)
                                .getJSONArray(SEARCH_ITEM_STORE_CONTACT)
                                .getString(ITEM_DEF));
                    }
                    item.setThumbnail(jsonItem.getJSONArray(SEARCH_ITEM_CONTAINER)
                            .getJSONObject(ITEM_DEF)
                            .getJSONArray(SEARCH_ITEM_CONTAINER_THNAIL)
                            .getJSONObject(THNAIL_SMALL)
                            .getString(SEARCH_ITEM_CONTAINER_THNAIL_VALUE));
                }
            }

        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public static void buildUri() {


    }
}
