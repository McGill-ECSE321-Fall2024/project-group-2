package ca.mcgill.ecse321.gamestore.dto;

import java.util.List;

public class LineItemListDto {
    private List<LineItemResponseDto> lineItems;

    public LineItemListDto(List<LineItemResponseDto> lineItems) {
        this.lineItems = lineItems;
    }

    public List<LineItemResponseDto> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItemResponseDto> lineItems) {
        this.lineItems = lineItems;
    }
}
