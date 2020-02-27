package hu.gyarmatip.simplepaymentapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionDto {

    private Double sum;
    private Long senderAccountId;
    private Long recipientAccountId;

}
