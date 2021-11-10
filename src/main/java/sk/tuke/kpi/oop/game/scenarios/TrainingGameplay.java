package sk.tuke.kpi.oop.game.scenarios;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.gamelib.map.MapMarker;
import sk.tuke.kpi.oop.game.*;

import java.util.Map;

public class TrainingGameplay extends Scenario implements SceneListener {
    @Override
    public void setupPlay(Scene scene){
        Map<String, MapMarker> markers = scene.getMap().getMarkers();

        MapMarker reactorArea1 = markers.get("reactor-area-1");
        MapMarker reactorArea2 = markers.get("reactor-area-2");

        MapMarker coolerArea1 = markers.get("cooler-area-1");
        MapMarker coolerArea2 = markers.get("cooler-area-2");
        MapMarker coolerArea3 = markers.get("cooler-area-3");

        MapMarker computerArea1 = markers.get("computer-area");


        Reactor reactor_1 = new Reactor();
        Reactor reactor_2 = new Reactor();

        scene.addActor(reactor_1, reactorArea1.getPosX(), reactorArea1.getPosY());
        scene.addActor(reactor_2, reactorArea2.getPosX(), reactorArea2.getPosY());
        reactor_1.turnOn();
        reactor_2.turnOn();

        Cooler cooler_1 = new Cooler(reactor_1);
        Cooler cooler_2 = new Cooler(reactor_2);
        Cooler cooler_3 = new Cooler(reactor_2);

        cooler_1.turnOn();
        cooler_2.turnOn();
        cooler_3.turnOn();

        Computer computer = new Computer();
        //computer.setPowered(true); не знаю точно нужно ли включить
        reactor_2.addDevice(computer);

        scene.addActor(computer, computerArea1.getPosX(), computerArea1.getPosY());


        scene.addActor(cooler_1, coolerArea1.getPosX(), coolerArea1.getPosY());
        scene.addActor(cooler_2, coolerArea2.getPosX(), coolerArea2.getPosY());
        scene.addActor(cooler_3, coolerArea3.getPosX(), coolerArea3.getPosY());

        //scene.addActor(cooler, 128, 128);


//        Teleport teleportA = new Teleport(null);
//        Teleport teleportB = new Teleport (teleportA);
//        scene.addActor(teleportA, 70, 240);
//        scene.addActor(teleportB, 240, 70);
//        teleportA.setDestination(teleportB);
        //getMarkers(scene);
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
