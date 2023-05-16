package benlinkurgra.deadwood;

import benlinkurgra.deadwood.controller.ActionProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MainTest {
    @Mock Display display;
    ActionProvider actionProvider;


    @BeforeEach
    public void beforeEach() {
    }

    // given: player starts turn at trailers
    // when: given actions
    // then: should only be able to move or end turn
    @Test
    public void actions_atTrailers() {

    }
    // given: player at location other than casting office
    // when: attempting upgrade
    // then: should refuse action

    // given: player at casting office
    // when: attempting upgrade
    // then: should only be able to perform action when player has upgrades

    // given: player at casting office and has upgrades
    // when: attempting upgrade
    // then: should only be able to select entry that meets requirements

    // given: player selects valid upgrade
    // when: performing upgrade
    // then: players appropriate currency should be reduced and players rank increased accordingly



    @Test
    public void movement_happyPath() {

    }


}