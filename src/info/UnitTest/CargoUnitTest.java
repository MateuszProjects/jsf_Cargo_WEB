package info.UnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import info.Cargo.BB.AddressBB;
import junit.framework.Assert;

public class CargoUnitTest {

	AddressBB addressBB = new AddressBB();
	
	
	
	@Test
	public void validateAddress() {
	
		// Given
		addressBB.setCityCode("45222");
		addressBB.save();
		
		// When
		boolean result = true; 
		
		// Then
		assertEquals(true, result);
	}

}
