package org.example.adapter.factory;


import org.example.adapter.PurchaseAdapter;
import org.example.adapter.PurchaseNoExtensionAdapter;
import org.example.adapter.PurchaseTxtAdapter;
import org.example.exception.FactoryException;

public class PurchaseAdapterFactory {

    public PurchaseAdapter createAdapter(String path) {
        int lastDotIndex = path.lastIndexOf('.');
        String extension;

        if (lastDotIndex == -1) {
            extension = "No extension";
        } else {
            extension = path.substring(lastDotIndex + 1);
        }

        return switch (extension) {
            case "txt" -> new PurchaseTxtAdapter();
            case "No extension" -> new PurchaseNoExtensionAdapter();
            default -> throw new FactoryException("Extension: \"" + extension + "\" is unsupported.");
        };
    }

}
