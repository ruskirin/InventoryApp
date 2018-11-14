package project.udacity.my.inventoryapp.http;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import project.udacity.my.inventoryapp.objects.EbayItem;
import project.udacity.my.inventoryapp.R;

/***
 * Contains:
 *  getEbayData - base JSON parser of a given query
 *  buildUri - build the URI for desired items
 */
public class JsonParser {

    public static List<EbayItem> getEbayData(String jsonResponse) {
        List<EbayItem> items = new ArrayList<>();

        final int ITEM_DEF = 0;
        final int THNAIL_MED = 1;
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
        final String SEARCH_ITEM_URL = "viewItemURL";
        final String SEARCH_ITEM_CONTAINER = "galleryInfoContainer";
        final String SEARCH_ITEM_CONTAINER_THNAIL = "galleryURL";
        final String SEARCH_ITEM_CONTAINER_THNAIL_VALUE = "__value__";

        /*
         For some reason the JSON response from this api wraps EVERYTHING in array brackets,
         to avoid instantiating all of the go-between objects I chain a lot of the calls
         */
        try {
            final String LOG_TAG = "JsonParser";
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject categoryObject = jsonObject.getJSONArray(MAIN_ARRAY).getJSONObject(ITEM_DEF);
            JSONArray code = categoryObject.getJSONArray(QUERY_RESPONSE);

            if(code.get(ITEM_DEF).toString().trim().equals("Success")) {
                JSONArray itemArray = categoryObject.getJSONArray(QUERY_SEARCH)
                        .getJSONObject(ITEM_DEF)
                        .getJSONArray(QUERY_SEARCH_ITEM);

                for (int i = 0; i < itemArray.length(); i++) {
                    EbayItem item = new EbayItem();
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

                    } else {
                        item.setSellerContact(jsonItem.getJSONArray(SEARCH_ITEM_URL)
                                .getString(ITEM_DEF));
                    }
                    item.setThumbnail(jsonItem.getJSONArray(SEARCH_ITEM_CONTAINER)
                            .getJSONObject(ITEM_DEF)
                            .getJSONArray(SEARCH_ITEM_CONTAINER_THNAIL)
                            .getJSONObject(THNAIL_MED)
                            .getString(SEARCH_ITEM_CONTAINER_THNAIL_VALUE));

                    items.add(item);
                }
            }

        } catch(JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static String buildUri(Context context, int category) {

        if(category == 0 || category == 1 || category == 2) {
            Uri uriBase = Uri.parse(context.getResources().getString(R.string.url_ebay_base));
            Uri.Builder uriBuilder = uriBase.buildUpon();

        /*
          If appending more than one of these values, concatenate an array index at the end
           starting from 0 for each successive value. This of course can be put into a method
           and automated but I wasn't going for so much detail.
            eg. outputSelector(0)= ..., outputSelector(1)=..., etc
            eg. itemFilter(0).name=..., itemFilter(0).value=..., itemFilter(1).name=..., etc
         */
            String outputSelector = context.getString(R.string.query_ebay_outputselector);
            String itemFilter = context.getString(R.string.query_ebay_itemfilter);
            String itemFilterName = context.getString(R.string.query_ebay_itemfilter_name);
            String itemFilterValue = context.getString(R.string.query_ebay_itemfilter_value);

            uriBuilder.appendQueryParameter(context.getString(R.string.Rquery_ebay_operation),
                    context.getString(R.string.value_ebay_operation_category));
            uriBuilder.appendQueryParameter(context.getString(R.string.value_ebay_responsetype),
                    context.getString(R.string.value_ebay_version_def));
            uriBuilder.appendQueryParameter(context.getString(R.string.Rquery_ebay_appid),
                    context.getString(R.string.api_id));
            uriBuilder.appendQueryParameter(context.getString(R.string.Rquery_ebay_responsetype),
                    context.getString(R.string.value_ebay_responsetype));
            uriBuilder.appendQueryParameter(context.getString(R.string.Rquery_ebay_payload),
                    context.getString(R.string.value_ebay_payload));
            uriBuilder.appendQueryParameter(context.getString(R.string.query_ebay_category),
                    context.getResources().getStringArray(R.array.array_category_ids)[category]);
            uriBuilder.appendQueryParameter(context.getString(R.string.query_ebay_paginput_entriespage),
                    context.getString(R.string.value_ebay_entriespage_def));
            uriBuilder.appendQueryParameter(context.getString(R.string.query_ebay_paginput_pagenum),
                    context.getString(R.string.value_ebay_pagenum_def));
            uriBuilder.appendQueryParameter(outputSelector.concat("(0)"),
                    context.getString(R.string.value_ebay_outputselector_seller));
            uriBuilder.appendQueryParameter(outputSelector.concat("(1)"),
                    context.getString(R.string.value_ebay_outputselector_store));
            uriBuilder.appendQueryParameter(outputSelector.concat("(2)"),
                    context.getString(R.string.value_ebay_outputselector_gallery));
            uriBuilder.appendQueryParameter(itemFilter.concat("(0)").concat(itemFilterName),
                    context.getString(R.string.value_ebay_itemfilter_name_condition));
            uriBuilder.appendQueryParameter(itemFilter.concat("(0)").concat(itemFilterValue),
                    context.getString(R.string.value_ebay_itemfilter_value_condition_new));
            uriBuilder.appendQueryParameter(itemFilter.concat("(1)").concat(itemFilterName),
                    context.getString(R.string.value_ebay_itemfilter_name_listingtype));
            uriBuilder.appendQueryParameter(itemFilter.concat("(1)").concat(itemFilterValue),
                    context.getString(R.string.value_ebay_itemfilter_value_listingtype));
            uriBuilder.appendQueryParameter(context.getString(R.string.query_ebay_searchitem),
                    context.getString(R.string.value_ebay_searchitem_currentprice));

            return uriBuilder.build().toString();
        }

        return null;
    }
}
