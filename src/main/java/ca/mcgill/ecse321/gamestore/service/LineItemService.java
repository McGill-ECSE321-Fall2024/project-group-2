package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.model.LineItem;
import ca.mcgill.ecse321.gamestore.repository.LineItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineItemService {

    @Autowired
    private LineItemRepository lineItemRepository;

    @Transactional
    public LineItem getLineItem(int id) {
        LineItem lineItem = lineItemRepository.findLineItemById(id);
        if (lineItem == null) {
            throw new IllegalArgumentException("LineItem not found");
        }
        return lineItem;
    }

    @Transactional
    public LineItem createLineItem(int quantity, double price) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        LineItem lineItem = new LineItem();
        lineItem.setQuantity(quantity);
        lineItem.setPrice(price);

        return lineItemRepository.save(lineItem);
    }

    @Transactional
    public LineItem updateLineItem(int id, int quantity, double price) {
        LineItem lineItem = lineItemRepository.findLineItemById(id);
        if (lineItem == null) {
            throw new IllegalArgumentException("LineItem not found");
        }
        lineItem.setQuantity(quantity);
        lineItem.setPrice(price);

        return lineItemRepository.save(lineItem);
    }

    @Transactional
    public boolean deleteLineItem(int id) {
        LineItem lineItem = lineItemRepository.findLineItemById(id);
        if (lineItem == null) {
            throw new IllegalArgumentException("LineItem not found");
        }
        lineItemRepository.delete(lineItem);
        return true;
    }
}
