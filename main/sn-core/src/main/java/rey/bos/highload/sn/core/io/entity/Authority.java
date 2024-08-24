package rey.bos.highload.sn.core.io.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "authorities")
@Builder
@Data
public class Authority {

    @Id
    private Long id;

    private String name;

}
