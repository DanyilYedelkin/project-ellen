package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.messages.Topic;

import java.util.Objects;
import java.util.function.Predicate;

public class Observing<A extends Actor, T> implements Behaviour<A> {
    private Topic<T> topic;             // téma správ, ktorú bude aktér sledovať
    private Predicate<T> predicate;     // predikát (podmienka) na overenie prijatej správy
    private Behaviour<A> delegate;      // zaobalené správanie
    private A actor;

    public Observing(Topic<T> topic, Predicate<T> predicate, Behaviour<A> delegate){
        this.topic = topic;
        this.predicate = predicate;
        this.delegate = delegate;
    }


    @Override
    public void setUp(A actor){
        if(actor != null){
            this.actor = actor;
            Objects.requireNonNull(actor.getScene()).getMessageBus().subscribe(topic, this::test);
        }
    }

    private void test(T work){
        if(actor != null && predicate.test(work)){
            delegate.setUp(actor);
        }
    }

}
