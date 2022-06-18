package base.Event;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Event {

    @NonNull int idEvent;
    @NonNull int idcalendar;
    @NonNull String nome;
    @NonNull String date;
    @NonNull String u;
    String type;
    String desc;

}
