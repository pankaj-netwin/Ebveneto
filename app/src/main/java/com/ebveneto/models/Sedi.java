package com.ebveneto.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mayur on 03-11-2017.
 */
public class Sedi implements Parcelable {
    private String Tipo;

    private String idSede;

    private String fonte;

    private String Cap;

    private String updUSER;

    private String Via;

    private String Frazione;

    private String Comune;

    private String Nome;

    private String updDATA;

    private String updPC;

    private String Prov;

    private String idAZ;

    public Sedi()
    {
    }

    public String getTipo ()
    {
        return Tipo;
    }

    public void setTipo (String Tipo)
    {
        this.Tipo = Tipo;
    }

    public String getIdSede ()
    {
        return idSede;
    }

    public void setIdSede (String idSede)
    {
        this.idSede = idSede;
    }

    public String getFonte ()
    {
        return fonte;
    }

    public void setFonte (String fonte)
    {
        this.fonte = fonte;
    }

    public String getCap ()
    {
        return Cap;
    }

    public void setCap (String Cap)
    {
        this.Cap = Cap;
    }

    public String getUpdUSER ()
    {
        return updUSER;
    }

    public void setUpdUSER (String updUSER)
    {
        this.updUSER = updUSER;
    }

    public String getVia ()
    {
        return Via;
    }

    public void setVia (String Via)
    {
        this.Via = Via;
    }

    public String getFrazione ()
    {
        return Frazione;
    }

    public void setFrazione (String Frazione)
    {
        this.Frazione = Frazione;
    }

    public String getComune ()
    {
        return Comune;
    }

    public void setComune (String Comune)
    {
        this.Comune = Comune;
    }

    public String getNome ()
    {
        return Nome;
    }

    public void setNome (String Nome)
    {
        this.Nome = Nome;
    }

    public String getUpdDATA ()
    {
        return updDATA;
    }

    public void setUpdDATA (String updDATA)
    {
        this.updDATA = updDATA;
    }

    public String getUpdPC ()
    {
        return updPC;
    }

    public void setUpdPC (String updPC)
    {
        this.updPC = updPC;
    }

    public String getProv ()
    {
        return Prov;
    }

    public void setProv (String Prov)
    {
        this.Prov = Prov;
    }

    public String getIdAZ ()
    {
        return idAZ;
    }

    public void setIdAZ (String idAZ)
    {
        this.idAZ = idAZ;
    }


    protected Sedi(Parcel in) {
        Tipo = in.readString();
        idSede = in.readString();
        fonte = in.readString();
        Cap = in.readString();
        updUSER = in.readString();
        Via = in.readString();
        Frazione = in.readString();
        Comune = in.readString();
        Nome = in.readString();
        updDATA = in.readString();
        updPC = in.readString();
        Prov = in.readString();
        idAZ = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Tipo);
        dest.writeString(idSede);
        dest.writeString(fonte);
        dest.writeString(Cap);
        dest.writeString(updUSER);
        dest.writeString(Via);
        dest.writeString(Frazione);
        dest.writeString(Comune);
        dest.writeString(Nome);
        dest.writeString(updDATA);
        dest.writeString(updPC);
        dest.writeString(Prov);
        dest.writeString(idAZ);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Sedi> CREATOR = new Parcelable.Creator<Sedi>() {
        @Override
        public Sedi createFromParcel(Parcel in) {
            return new Sedi(in);
        }

        @Override
        public Sedi[] newArray(int size) {
            return new Sedi[size];
        }
    };
}

