package ds.borda.jh.app.service.mapper;

import ds.borda.jh.app.domain.*;
import ds.borda.jh.app.service.dto.VentaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Venta} and its DTO {@link VentaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VentaMapper extends EntityMapper<VentaDTO, Venta> {



    default Venta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Venta venta = new Venta();
        venta.setId(id);
        return venta;
    }
}
