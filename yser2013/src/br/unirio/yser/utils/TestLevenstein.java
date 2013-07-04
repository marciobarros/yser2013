package br.unirio.yser.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestLevenstein
{
	@Test
	public void testBasic()
	{
		DistanciaLevenstein dl = new DistanciaLevenstein();
		assertEquals(3, dl.calcula("abc", "xyz"));
		assertEquals(1, dl.calcula("marcio", "márcio"));
		assertEquals(1, dl.calcula("marcio", "marcios"));
		assertEquals(2, dl.calcula("marcixo", "marcxio"));
		assertEquals(4, dl.calcula("ADLEMAN, L.", "LAYMAN, L."));
	}
}