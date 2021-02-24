package com.fashion.core.ui;

import com.fashion.ui.dialog.DialogLoading;
import com.fashion.ui.dialog.DialogMessage;
import com.fashion.ui.view_model.NanoViewModel;
import com.fashion.utils.Navigator;

public interface HandleViewModelBasic {
    default void handleViewModelBase(BaseActivity context, NanoViewModel nanoViewModel) {
        if (nanoViewModel != null && context != null) {
            handleMessage(context, nanoViewModel);
            handleMessageId(context, nanoViewModel);
            handUnauthorized(context, nanoViewModel);
            handIsLoading(context, nanoViewModel);
        } else {
            System.out.println("Error");
        }
    }

    default void handleMessageId(BaseActivity context, NanoViewModel nanoViewModel) {
        nanoViewModel.idMessage.observe(context, messageId -> {
            if (messageId != 0)
                new DialogMessage(context.getString(messageId)).open(context.getSupportFragmentManager());
        });
    }

    default void handleMessage(BaseActivity context, NanoViewModel nanoViewModel) {
        nanoViewModel.message.observe(context, message -> {
            if (message != null)
                new DialogMessage(message).open(context.getSupportFragmentManager());
        });
    }

    default void handUnauthorized(BaseActivity context, NanoViewModel nanoViewModel) {
        nanoViewModel.isLogoutRequired.observe(context, isLogoutRequired -> {
            if (isLogoutRequired) {
                nanoViewModel.isLogoutRequired.postValue(false);
                Navigator.pushToWelcome(context);
            }
        });
    }

    default void handIsLoading(BaseActivity context, NanoViewModel nanoViewModel) {
        nanoViewModel.isLoading.observe(context, isLoading -> {
            if (isLoading) {
                if (context.dialogLoading == null)
                    context.dialogLoading = new DialogLoading();
                context.getSupportFragmentManager().beginTransaction().remove(context.dialogLoading).commit();
                context.dialogLoading.open(context.getSupportFragmentManager());

            } else {
                if (context.dialogLoading != null) {
                    context.dialogLoading.close();
                }
            }
        });
    }
}
