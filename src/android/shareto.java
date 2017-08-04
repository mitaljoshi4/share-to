package shareto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Base64;
import android.widget.ImageView;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * This class echoes a string called from JavaScript.
 */
public class shareto extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (action.equals("shared")) {
            this.shared(callbackContext);
            return true;
        }
        return false;
    }

    private void shared(CallbackContext callbackContext) {

        Intent intent = cordova.getActivity().getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                String image = handleSendImage(intent);
                callbackContext.success(image);
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                JSONArray images = handleSendMultipleImages(intent);
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, images));
            }
        } else {
            callbackContext.error("No Shared image found.");
        }
    }

    private String handleSendImage(Intent intent) {
        Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        return uriToBase64Converter(imageUri);
    }

    private JSONArray handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        JSONArray uriList = new JSONArray();
        for (int i = 0; i < imageUris.size(); i++) {
            String imagebase = uriToBase64Converter(imageUris.get(i));
            uriList.put(imagebase);
        }
        return uriList;
    }

    private String uriToBase64Converter(Uri imageUri) {
        ImageView iv = new ImageView(cordova.getActivity());
        iv.setImageURI(imageUri);
        BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bb = bos.toByteArray();
        String image = Base64.encodeToString(bb, 1);
        return "data:image/png;base64," + image;
    }
}
