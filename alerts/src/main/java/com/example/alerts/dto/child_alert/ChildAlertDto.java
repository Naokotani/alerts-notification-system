package com.example.alerts.dto.child_alert;
import lombok.Data;
import java.util.List;

@Data
public class ChildAlertDto {
    List<ChildAlertPersonDto> children;
    List<ChildAlertPersonDto> adults;

    public ChildAlertDto(List<ChildAlertPersonDto> children, List<ChildAlertPersonDto> adults) {
        this.children = children;
        this.adults = adults;
    }
}
