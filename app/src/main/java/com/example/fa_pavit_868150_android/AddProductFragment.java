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


public class AddProductFragment extends Fragment {

    private EditText etProductid, etProductName, etProductDescription, etProductPrice, etProductLatitude, etProductLongitude;
    private OnProductAddedListener onProductAddedListener;

    public interface OnProductAddedListener {
        void onProductAdded(Product product);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        etProductid = view.findViewById(R.id.et_product_id);
        etProductName = view.findViewById(R.id.et_product_name);
        etProductDescription = view.findViewById(R.id.et_product_description);
        etProductPrice = view.findViewById(R.id.et_product_price);
        etProductLatitude = view.findViewById(R.id.et_product_latitude);
        etProductLongitude = view.findViewById(R.id.et_product_longitude);
        Button btnAddProduct = view.findViewById(R.id.btn_add_product);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
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

                Product product = new Product(id, name, description, price, latitude, longitude);
                if (onProductAddedListener != null) {
                    onProductAddedListener.onProductAdded(product);
                }

                // dismiss the fragment
                getFragmentManager().beginTransaction().remove(AddProductFragment.this).commit();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProductAddedListener) {
            onProductAddedListener = (OnProductAddedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnProductAddedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onProductAddedListener = null;
    }
}