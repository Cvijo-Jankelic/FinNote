package com.project.finnote.utils;

import javafx.application.Platform;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Utility za pokretanje pozadinskih procesa uz sigurno ažuriranje GUI-ja.
 */
public class ProcessExecutor {

    /** Fiksni pool dretvi za naše pozadinske poslove */
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    private ProcessExecutor() { /* singleton util */ }

    /**
     * Pokrene pozadinski posao i na kraju rezultata ili greške pozove UI handler-e.
     *
     * @param job       pozadinska logika, vraća rezultat tipa R
     * @param onSuccess callback koji se poziva na JavaFX threadu s rezultatom
     * @param onError   callback koji se poziva na JavaFX threadu pri iznimci
     * @param <R>       tip rezultata
     */
    public static <R> void run(
            Callable<R> job,
            Consumer<R> onSuccess,
            Consumer<Throwable> onError
    ) {
        EXECUTOR.submit(() -> {
            try {
                R result = job.call();
                Platform.runLater(() -> onSuccess.accept(result));
            } catch (Throwable t) {
                Platform.runLater(() -> onError.accept(t));
            }
        });
    }

    /**
     * Posebni overload za poslove koji ne vraćaju vrijednost.
     */
    public static void run(
            Runnable job,
            Runnable onSuccess,
            Consumer<Throwable> onError
    ) {
        EXECUTOR.submit(() -> {
            try {
                job.run();
                Platform.runLater(onSuccess);
            } catch (Throwable t) {
                Platform.runLater(() -> onError.accept(t));
            }
        });
    }
}
