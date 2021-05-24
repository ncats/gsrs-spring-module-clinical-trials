package gov.nih.ncats.clinicaltrial.inheritance;

import gsrs.controller.GetGsrsRestApiMapping;
import gsrs.controller.GsrsRestApiController;
import gsrs.controller.IdHelpers;
import gsrs.legacy.LegacyGsrsSearchService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@GsrsRestApiController(context = ShapeEntityService.CONTEXT,  idHelper = IdHelpers.NUMBER)
@ExposesResourceFor(Shape.class)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ShapeController {

    @Autowired
    private ShapeRepository shapeRepository;
    @Autowired
    private CircleRepository circleRepository;
    @Autowired
    private SquareRepository squareRepository;

    @Autowired
    private SquareEntityService squareEntityService;


    @Autowired
    private CircleEntityService circleEntityService;

    @Autowired
    private EntityLinks entityLinks;

    @Autowired
    private Environment env;


    public ShapeController() {

    }


    protected Stream<Shape> filterStream(Stream<Shape> stream, boolean publicOnly, Map<String, String> parameters) {
        return stream;
    }


    @GetGsrsRestApiMapping("/@tryshape")
    public JSONObject tryShape() {

        List messages = new ArrayList<String>();
        List errors = new ArrayList<String>();
        HttpStatus status = HttpStatus.OK;
/*
        Circle circle1 = new Circle();
        circle1.trialNumber = "circle1";
        circle1.name = "pizza";
        circleRepository.save(circle1);

        Square square2 = new Square();
        square2.trialNumber = "square2";
        square2.name = "boxing ring";
        squareRepository.save(square2);

        Square square3 = new Square();
        square3.trialNumber = "square3";
        square3.name = "window";
        squareRepository.save(square3);

        // Access by subclass
        System.out.println("Count circles: "+ circleRepository.count());
        System.out.println("Count squares: "+ circleRepository.count());

        // Access in aggregate by baseclass

        System.out.println("Count shapes: "+ shapeRepository.count());

        List<Shape> shapes = shapeRepository.findAll();
        for(Shape s: shapes) {
            System.out.println("Shape id|name " + s.getTrialNumber() +"|"+ s.getName());
        }
*/

        Circle circle1 = new Circle();
        circle1.trialNumber = "circle1";
        circle1.name = "pizza";
        circleEntityService.create(circle1);

        Square square2 = new Square();
        square2.trialNumber = "square2";
        square2.name = "boxing ring";
        squareEntityService.create(square2);

        Square square3 = new Square();
        square3.trialNumber = "square3";
        square3.name = "window";
        squareEntityService.create(square3);

        // Access by subclass
        System.out.println("Count circles: "+ circleRepository.count());
        System.out.println("Count squares: "+ circleRepository.count());

        // Access in aggregate by baseclass

        System.out.println("Count shapes: "+ shapeRepository.count());

        List<Shape> shapes = shapeRepository.findAll();
        for(Shape s: shapes) {
            System.out.println("Shape id|name " + s.getTrialNumber() +"|"+ s.getName());
        }


        Map<String, String> hm = new Hashtable<String, String>();
        hm.put("one", "a");
        hm.put("two", "b");
        return new JSONObject(hm);
    }


}







