package ua.netcracker.group3.automaticallytesting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
  Класс имеет те же поля, что и сущность Project в ER диаграмме. Их только пять.
  Поле status наверняка должно быть типом Enum, но ещё неизвестно, какие именно статусы будут.
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    private long id;
    private String name;
    private String link;
    private String status;
    private long userId;

}
