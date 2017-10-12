package com.wrox.begspring;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.matcher.Matchers;

public class CalculateModule extends AbstractModule {

	@Override
	protected void configure() {

		/*
		 * Association de Operation (interface) a l'un de: OpMultiply (classe
		 * concrete pour mulitplier) OpAdd (classe concrete pour additionner)
		 */
		bind(Operation.class).to(OpMultiply.class);

		/*
		 * Association de ResultWriter (interface) a l'un de: ScreenWriter
		 * (classe concrete pour produire la sortie a l'ecran) DataFileWriter
		 * (classe concrete pour produire la sortie dans un fichier output.txt
		 * dans le repertoire d'ou l'application est demarree).
		 */
		bind(ResultWriter.class).to(ScreenWriter.class);

		/*
		 * Association de notre intercepteur de journalisation,
		 * LoggingInterceptor, a toutes les methodes de toute sous-classe de
		 * Operation (i.e., toute classe implementant l'interface Operation)
		 */
		bindInterceptor(Matchers.subclassesOf(Operation.class), Matchers.any(),
				new LoggingInterceptor());
	}

	public static void main(String[] args) {
		/*
		 * Guice.createInjector() accepte des Modules, et retourne une nouvelle
		 * instance d'Injector. La plupart des applications invoqueront cette
		 * methode exactement une fois dans leur methode main().
		 */
		Injector injector = Guice.createInjector(new CalculateModule());

		/*
		 * Une fois l'injecteur cree, on peut assembler des objets.
		 */
		CalculateGuice calc = injector.getInstance(CalculateGuice.class);

		/*
		 * Invocation de l'operation.
		 */
		calc.execute(args);
	}
}