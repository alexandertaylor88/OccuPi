package occupi.occupi;

import android.os.Parcel;
import android.os.Parcelable;


public class Contact implements Parcelable {

    public String id, name, phone;

    Contact(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    protected Contact(Parcel in) {
        id = in.readString();
        name = in.readString();
        phone = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public String toString() {
        return name + " : " + phone;
    }
    public String number() {
        return phone;
    }
    public String name() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(phone);
    }
}