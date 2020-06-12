package com.rezende.chamados.model;

import java.io.Serializable;

public class PersonBEAN implements Serializable {

    private String userID;
    private String authIdPerson;
    private String name;
    private String cpf;
    private String cellPhone;
    private String email;
    private int sex;
    private String address;
    private String latitude;
    private String longitude;

    public PersonBEAN() {

    }

    public PersonBEAN(String authIdPerson, String email) {
        setAuthIdPerson(authIdPerson);
        setEmail(email);
    }

    public PersonBEAN(String authIdPerson, String name, String cpf, String cellPhone, String email, int sex/*, String address, String latitude, String longitude*/) {
        setAuthIdPerson(authIdPerson);
        setName(name);
        setCpf(cpf);
        setCellPhone(cellPhone);
        setEmail(email);
        setSex(sex);
        /*setAddress(address);
        setLatitude(latitude);
        setLongitude(longitude);*/
    }

    public PersonBEAN(String authIdPerson, String name, String email) {
        setAuthIdPerson(authIdPerson);
        setName(name);
        setEmail(email);
    }

    /*protected PersonBEAN(Parcel in) {
        userID = in.readString();
        authIdPerson = in.readString();
        name = in.readString();
        cpf = in.readString();
        cellPhone = in.readString();
        email = in.readString();
        sex = in.readInt();
        address = in.readString();
    }

    public static final Creator<PersonBEAN> CREATOR = new Creator<PersonBEAN>() {
        @Override
        public PersonBEAN createFromParcel(Parcel in) {
            return new PersonBEAN(in);
        }

        @Override
        public PersonBEAN[] newArray(int size) {
            return new PersonBEAN[size];
        }
    };*/

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAuthIdPerson() {
        return authIdPerson;
    }

    public void setAuthIdPerson(String authIdPerson) {
        this.authIdPerson= authIdPerson;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "PersonBEAN{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    /*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userID);
        dest.writeString(authIdPerson);
        dest.writeString(name);
        dest.writeString(cpf);
        dest.writeString(cellPhone);
        dest.writeString(email);
        dest.writeInt(sex);
        dest.writeString(address);
    }*/
}
