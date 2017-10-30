package br.ufpe.cin.if710.testing.junit;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class PessoaTest {

    List<Pessoa> pessoas = new LinkedList<>();

    @BeforeClass
    static public void rodaApenasUmaVezNoInicio() {
        //roda apenas uma vez, para configuracao geral
    }

    @Before
    public void antesDeCadaMetodoTest() {
        pessoas.add(new Pessoa("Leopoldo","lmt"));
        pessoas.add(new Pessoa("Teste","txz"));
    }

    @After
    public void aposCadaMetodoTest() {
        pessoas.clear();
    }

    @AfterClass
    static public void rodaApenasUmaVezNoFinal() {
        // roda apenas uma vez, apos todos os metodos de teste terem rodado
    }

    @Test
    public void testePrimeiroElemento() {
        Pessoa p = pessoas.get(0);
        Assert.assertEquals("algo muito estranho aconteceu...", "Leopoldo", p.getNome());
    }

    @Test
    public void testeQueVaiFalhar() {
        Pessoa p = pessoas.get(1);
        Assert.assertEquals("algo muito estranho aconteceu...", "Leopoldo", p.getNome());
    }

    @Test
    public void testeObjetoNaoNulo() {
        Pessoa p = pessoas.get(0);
        Assert.assertNotNull("deu ruim", p);
    }
}
