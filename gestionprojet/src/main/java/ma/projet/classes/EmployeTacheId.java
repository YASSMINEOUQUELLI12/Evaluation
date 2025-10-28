package ma.projet.classes;



import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmployeTacheId implements Serializable {
    private Integer employeId;
    private Integer tacheId;

    public EmployeTacheId() {}
    public EmployeTacheId(Integer e, Integer t){ this.employeId=e; this.tacheId=t; }

    // equals/hashCode
    @Override public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof EmployeTacheId)) return false;
        EmployeTacheId that=(EmployeTacheId)o;
        return Objects.equals(employeId, that.employeId)
                && Objects.equals(tacheId, that.tacheId);
    }
    @Override public int hashCode(){ return Objects.hash(employeId, tacheId); }
}

