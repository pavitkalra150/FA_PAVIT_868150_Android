package com.example.fa_pavit_868150_android;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class EditProductFragment extends Fragment {

    private EditText etProductid, etProductName, etProductDescription, etProductPrice, etProductLatitude, etProductLongitude;
    private OnProductEditedListener onProductEditedListener;
    private Product product;

    public interface OnProductEditedListener {
        void onProductEdited(Product product);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = getArguments().getParcelable("product");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_edit_product, container, false);
        product = new Product();
        product.setId(getArguments().getInt("id"));
        product.setName(getArguments().getString("name"));
        product.setDescription(getArguments().getString("description"));
        product.setPrice(getArguments().getDouble("price"));
        product.setLatitude(getArguments().getDouble("latitude"));
        product.setLongitude(getArguments().getDouble("longitude"));

        etProductid = view.findViewById(R.id.et_edit_product_id);
        etProductName = view.findViewById(R.id.et_edit_product_name);
        etProductDescription = view.findViewById(R.id.et_edit_product_description);
        etProductPrice = view.findViewById(R.id.et_edit_product_price);
        etProductLatitude = view.findViewById(R.id.et_edit_product_latitude);
        etProductLongitude = view.findViewById(R.id.et_edit_product_longitude);
        Button btnEditProduct = view.findViewById(R.id.btn_update_product);
        Button deleteProduct = view.findViewById(R.id.btn_delete_product);
        etProductid.setText(String.valueOf(product.getId()));
        etProductName.setText(product.getName());
        etProductDescription.setText(product.getDescription());
        etProductPrice.setText(String.valueOf(product.getPrice()));
        etProductLatitude.setText(String.valueOf(product.getLatitude()));
        etProductLongitude.setText(String.valueOf(product.getLongitude()));
//        deleteProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Delete the product from the database
//                DatabaseHelper dbHelper = new DatabaseHelper(getContext());
//                dbHelper.deleteProduct(product.getId());
//
//                // Dismiss the fragment and notify the listener that the product has been deleted
//                if (onProductEditedListener != null) {
//                    onProductEditedListener.onProductDeleted(product);
//                }
//                getFragmentManager().beginTransaction().remove(EditProductFragment.this).commit();
//            }
//        });
        btnEditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(etProductid.getText().toString());
                String name = etProductName.getText().toString();
                String description = etProductDescription.getText().toString();
                String priceString = etProductPrice.getText().toString();
                String latitudeString = etProductLatitude.getText().toString();
                String longitudeString = etProductLongitude.getText().toString();

                double price = 0;
                double latitude = 0;
                double longitude = 0;

                try {
                    price = Double.parseDouble(priceString);
                    latitude = Double.parseDouble(latitudeString);
                    longitude = Double.parseDouble(longitudeString);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                product.setId(id);
                product.setName(name);
                product.setDescription(description);
                product.setPrice(price);
                product.setLatitude(latitude);
                product.setLongitude(longitude);

                if (onProductEditedListener != null) {
                    onProductEditedListener.onProductEdited(product);
                }

                // dismiss the fragment
                getFragmentManager().beginTransaction().remove(EditProductFragment.this).commit();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProductEditedListener) {
            onProductEditedListener = (OnProductEditedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnProductEditedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onProductEditedListener = null;
    }
}