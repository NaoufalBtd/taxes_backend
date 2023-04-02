package taxes.ws.converter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConverter<T, DTO> {
    abstract DTO toDto(T item);

    abstract T toItem(DTO dto);

    public List<DTO> toDto(List<T> items) {
        List<DTO> dtos = new ArrayList<>();
        if (items != null) {
            for (T item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }

    public List<T> toItem(List<DTO> dtos) {
        List<T> items = new ArrayList<>();
        if (dtos != null) {
            for (DTO dto : dtos) {
                T t = toItem(dto);
                items.add(t);
            }
        }
        return items;
    }

}
