/**
 * 
 */
package ubu.gii.dass.c01;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	@DisplayName("testAcquireReusable - Ricardo")
	public void testAcquireReusable() throws NotFreeInstanceException {
		ReusablePool pool = ReusablePool.getInstance();
		final Reusable reusable = pool.acquireReusable();
		assertNotNull(reusable, "El objeto adquirido no debe ser nulo.");
	}

	/**
	 * Test method for
	 * {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	@DisplayName("testReleaseReusable - MigueJodar")
	public void testReleaseReusable() {
		ReusablePool pool = ReusablePool.getInstance();
		java.util.List<Reusable> acquired = new java.util.ArrayList<>();
		try {
			while (true) {
				acquired.add(pool.acquireReusable());
			}
		} catch (NotFreeInstanceException e) {
			// pool drained
		}

		assertTrue(acquired.size() > 0, "No se pudo adquirir ninguna instancia para la prueba.");

		Reusable r = acquired.get(0);

		assertDoesNotThrow(() -> pool.releaseReusable(r));

		Reusable reacquired = assertDoesNotThrow(() -> pool.acquireReusable());
		assertSame(r, reacquired, "Al liberar y volver a adquirir se debe obtener la misma instancia.");

		// Ahora probar la excepción al liberar dos veces la misma instancia
		assertDoesNotThrow(() -> pool.releaseReusable(reacquired));
		assertThrows(DuplicatedInstanceException.class, () -> pool.releaseReusable(reacquired));
	}

	/**
	 * Test que comprueba que no se permite liberar una referencia null en el pool.
	 * Si el método aceptase null, el pool podría almacenar un objeto inválido y
	 * devolver null posteriormente al llamar a acquireReusable().
	 */
	@Test
	@DisplayName("releaseReusable no debe aceptar null - Guillermo")
	public void testReleaseReusableNoAceptaNull() throws Exception {
		resetSingletonPoolForGuillermoTest();

		ReusablePool pool = ReusablePool.getInstance();

		assertThrows(IllegalArgumentException.class, () -> pool.releaseReusable(null),
				"No se debe permitir liberar una instancia null en el pool.");

		Reusable r1 = pool.acquireReusable();
		Reusable r2 = pool.acquireReusable();

		assertNotNull(r1, "La primera instancia válida no debe ser null.");
		assertNotNull(r2, "La segunda instancia válida no debe ser null.");

		assertThrows(NotFreeInstanceException.class, () -> pool.acquireReusable(),
				"Después de adquirir las dos instancias válidas, el pool debe estar vacío.");

		resetSingletonPoolForGuillermoTest();
	}

	private void resetSingletonPoolForGuillermoTest() throws Exception {
		java.lang.reflect.Field instance = ReusablePool.class.getDeclaredField("instance");
		instance.setAccessible(true);
		instance.set(null, null);
	}
}
