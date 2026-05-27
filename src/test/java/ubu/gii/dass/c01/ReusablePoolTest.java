/**
 * 
 */
package ubu.gii.dass.c01;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Zeldan Campos
 *
 */
public class ReusablePoolTest {

	@BeforeAll
	public static void setUp() {
	}

	@AfterAll
	public static void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	@DisplayName("testGetInstance")
	public void testGetInstance() {
		ReusablePool i1 = ReusablePool.getInstance();
		ReusablePool i2 = ReusablePool.getInstance();
		assertNotNull(i1, "La instancia 1 no debe ser nula.");
		assertNotNull(i2, "La instancia 2 no debe ser nula.");
		assertSame(i1, i2, "Las dos instancias deben ser el mismo objeto (Singleton)");
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */

	@Test
	@DisplayName("testAcquireReusable")
	public void testAcquireReusable() {
    	ReusablePool pool = ReusablePool.getInstance();
    	final Reusable reusable = pool.acquireReusable();
    	assertNotNull(reusable, "El objeto adquirido no debe ser nulo.");
	}


	}

	/**
	 * Test method for
	 * {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	@DisplayName("testReleaseReusable")
	@Disabled("Not implemented yet")
	public void testReleaseReusable() {

	}

}
