package model;

/**
 * Represents an agency
 */
public class Agency {

    private String id;
    private String name;
    private String postalCode;
    private float longitude;
    private float latitude;
    private int nbPersons;

    public Agency(
            String id,
            String name,
            String postalCode,
            float longitude,
            float latitude,
            int nbPersons) {
        this.id = id;
        this.name = name;
        this.postalCode = postalCode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.nbPersons = nbPersons;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public String getPostalCode() {
        return postalCode;
    }

    public float getLongitude() {
        return longitude;
    }
    
    public float getLatitude() {
        return latitude;
    }

    public int getNbPersons() {
        return nbPersons;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + Float.floatToIntBits(latitude);
        result = prime * result + Float.floatToIntBits(longitude);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + nbPersons;
        result = prime * result
                + ((postalCode == null) ? 0 : postalCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Agency)) {
            return false;
        }
        Agency other = (Agency) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (Float.floatToIntBits(latitude) != Float
                .floatToIntBits(other.latitude)) {
            return false;
        }
        if (Float.floatToIntBits(longitude) != Float
                .floatToIntBits(other.longitude)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (nbPersons != other.nbPersons) {
            return false;
        }
        if (postalCode == null) {
            if (other.postalCode != null) {
                return false;
            }
        } else if (!postalCode.equals(other.postalCode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[Agence (" + id + ")]";
    }

}
