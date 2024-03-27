package wtf.nano.pdxvoice.lib;

import lombok.NonNull;

public interface IStateCallbacks {
    void onConnect();

    void onLoginStateChanged(final int state);

    void onErrorReceived(
            final int code,
            @NonNull final String error
    );
}