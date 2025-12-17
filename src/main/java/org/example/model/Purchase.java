package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Purchase {
    private LocalDateTime date;
    private String customerName;
    private long quantity;
}
