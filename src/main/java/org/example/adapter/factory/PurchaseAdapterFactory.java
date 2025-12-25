package org.example.adapter.factory;


import org.example.adapter.PurchaseAdapter;
import org.example.adapter.PurchaseNoExtensionAdapter;
import org.example.adapter.PurchaseTxtAdapter;
import org.example.exception.FactoryException;

public class PurchaseAdapterFactory {

    public PurchaseAdapter createAdapter(String path) {
        String extension = getExtension(path);

        return switch (extension) {
            case "txt" -> new PurchaseTxtAdapter();
            case "" -> new PurchaseNoExtensionAdapter();
            default -> throw new FactoryException("Extension: \"" + extension + "\" is unsupported.");
        };
    }

    private String getExtension(String path) {
        int lastDotIndex = path.lastIndexOf('.');

        if (lastDotIndex == -1) {
            return "";
        } else {
            return path.substring(lastDotIndex + 1);
        }
    }

}
