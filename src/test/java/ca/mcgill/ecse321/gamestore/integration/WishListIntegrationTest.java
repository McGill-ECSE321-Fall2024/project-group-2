package ca.mcgill.ecse321.gamestore.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gamestore.dto.WishListRequestDto;
import ca.mcgill.ecse321.gamestore.dto.WishListResponseDto;
import ca.mcgill.ecse321.gamestore.repository.WishListRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WishListIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WishListRepository wishListRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        wishListRepository.deleteAll();
    }

    // test to create a wishList with a valid name
    @Test
    public void testCreateWishList_Success() {
        WishListRequestDto request = new WishListRequestDto(1, "wishList1");
        ResponseEntity<WishListResponseDto> response = restTemplate.postForEntity("/wishlist", request, WishListResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        WishListResponseDto createdWishlist = response.getBody();
        assertNotNull(createdWishlist);
        assertEquals("wishList1", createdWishlist.getWishName());
    }

    // test to create a wishList with a null name
    @Test
    public void testCreateWishList_InvalidName() {
        WishListRequestDto request = new WishListRequestDto(1, null);
        ResponseEntity<String> response = restTemplate.postForEntity("/wishlist", request, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The name cannot be empty!", response.getBody());
    }

    // test to get a wishList with a valid Id
    @Test
    public void testGetWishListById() {
        WishListRequestDto request = new WishListRequestDto(1, "wishList1");
        ResponseEntity<WishListResponseDto> postResponse = restTemplate.postForEntity("/wishlist", request, WishListResponseDto.class);
        WishListResponseDto createdWishList = postResponse.getBody();

        ResponseEntity<WishListResponseDto> responseEntity = restTemplate.getForEntity(
                "/wishList/" + createdWishList.getId(), WishListResponseDto.class);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        WishListResponseDto retrievedWishList = responseEntity.getBody();
        assertNotNull(retrievedWishList);
        assertEquals("wishList1", retrievedWishList.getWishName());
    }

    // test to get a wishList with an invalid Id
    @Test
    public void testGetWishListById_NotFound() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/wishlist/99", String.class);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Can't find wishlist with the given Id!", responseEntity.getBody());
    }

    // test to update a valid Id wishList name witha a valid name
    @Test
    public void testUpdateWishListName_Success() {
        WishListRequestDto request = new WishListRequestDto(1, "wishList1");
        ResponseEntity<WishListResponseDto> postResponse = restTemplate.postForEntity("/wishlist", request, WishListResponseDto.class);
        WishListResponseDto createdWishList = postResponse.getBody();

        ResponseEntity<WishListResponseDto> responseEntity = restTemplate.exchange(
                "/wishlist/" + createdWishList.getId() + "?newName=wishList2",
                org.springframework.http.HttpMethod.PUT,
                null,
                WishListResponseDto.class
        );
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        WishListResponseDto updatedWishList = responseEntity.getBody();
        assertNotNull(updatedWishList);
        assertEquals("wishList2", updatedWishList.getWishName());
    }

    // test to update a wishList name with a null name
    @Test
    public void testUpdateWishListName_InvalidName() {
        WishListRequestDto request = new WishListRequestDto(1, "wishList1");
        ResponseEntity<WishListResponseDto> postResponse = restTemplate.postForEntity("/wishlist", request, WishListResponseDto.class);
        WishListResponseDto createdWishList = postResponse.getBody();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/wishList/" + createdWishList.getId() + "?newName=",
                org.springframework.http.HttpMethod.PUT,
                null,
                String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("The name cannot be empty!", responseEntity.getBody());
    }

    // test to update a wishList name with an invalid Id
    @Test
    public void testUpdateWishListName_NotFound() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/wishlist/99?newName=RPG",
                org.springframework.http.HttpMethod.PUT,
                null,
                String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Can't find wishlist with the given Id!", responseEntity.getBody());
    }

    // test to delete a wishList with a valid Id
    @Test
    public void testDeleteWishList_Success() {
        WishListRequestDto request = new WishListRequestDto(1, "wishList1");
        ResponseEntity<WishListResponseDto> postResponse = restTemplate.postForEntity("/wishlist", request, WishListResponseDto.class);
        WishListResponseDto createdWishList = postResponse.getBody();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/wishlist/" + createdWishList.getId(), org.springframework.http.HttpMethod.DELETE, null, String.class);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("WishList was deleted successfully.", responseEntity.getBody());
    }

    // test to delete a wishList with an invalid Id
    @Test
    public void testDeleteWishList_NotFound() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/wishlist/99",
                org.springframework.http.HttpMethod.DELETE,
                null,
                String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Can't find wishlist with the given Id!", responseEntity.getBody());
    }

}
