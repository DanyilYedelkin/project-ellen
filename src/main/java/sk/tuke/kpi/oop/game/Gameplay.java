package sk.tuke.kpi.oop.game;

//import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
//import sk.tuke.kpi.gamelib.actions.ActionSequence;
//import sk.tuke.kpi.gamelib.actions.Invoke;
//import sk.tuke.kpi.gamelib.actions.Wait;
//import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.gamelib.map.MapMarker;
//import sk.tuke.kpi.oop.game.tools.Hammer;

import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;

public class Gameplay extends Scenario {
    @Override
    public void setupPlay(Scene scene){
        //add reactor into the scene
        //Reactor reactor = new Reactor();
        /*scene.addActor(reactor, 146, 95);
        reactor.turnOn();

        // add cooler into the scene, and turn it on 5 seconds after the scene starts
        Cooler cooler = new Cooler(reactor);
        scene.addActor(cooler, 250,120);
        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(cooler::turnOn)
        ).scheduleFor(cooler);

        // for repairing reactor with a hammer
        Hammer hammer = new Hammer();
        scene.addActor(hammer, 64, 64);
        new When<>(
            () -> reactor.getTemperature() >= 3000,
            new Invoke<>(() -> reactor.repair(hammer))
        ).scheduleFor(reactor);

        // add smart cooler
        /*SmartCooler smartCooler = new SmartCooler(reactor);
        scene.addActor(smartCooler, 250, 200);
        new When<>(
            () -> reactor.getTemperature() >= 4000,
            new Invoke<>(() -> smartCooler.turnOn())
        ).scheduleFor(smartCooler);*/

        getMarkers(scene);

    }

    public void getMarkers(Scene scene){
        Map<String, MapMarker> markers = scene.getMap().getMarkers();
        // obtaining reference to marker named "reactor-area-1"
        MapMarker reactorArea1 = markers.get("reactor-area-1");
        MapMarker reactorArea2 = markers.get("reactor-area-2");
        MapMarker coolerArea1 = markers.get("cooler-area-1");
        MapMarker coolerArea2 = markers.get("cooler-area-2");
        MapMarker coolerArea3 = markers.get("cooler-area-3");
        MapMarker computerArea = markers.get("computer-area");


        Reactor reactor1 = new Reactor();
        Reactor reactor2 = new Reactor();
        Cooler cooler1 = new Cooler(reactor1);
        Cooler cooler2 = new Cooler(reactor2);
        Cooler cooler3 = new Cooler(reactor2);
        Computer computer = new Computer();

        scene.addActor(reactor1, reactorArea1.getPosX(), reactorArea1.getPosY());
        scene.addActor(reactor2, reactorArea2.getPosX(), reactorArea2.getPosY());
        scene.addActor(cooler1, coolerArea1.getPosX(), coolerArea1.getPosY());
        scene.addActor(cooler2, coolerArea2.getPosX(), coolerArea2.getPosY());
        scene.addActor(cooler3, coolerArea3.getPosX(), coolerArea3.getPosY());
        scene.addActor(computer, computerArea.getPosX(), computerArea.getPosY());
    }

}
