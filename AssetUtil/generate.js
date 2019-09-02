var handlebars = require("handlebars");
var fs = require("fs");


function isObject(obj) {
	var type = typeof obj;
	return type === 'function' || type === 'object' && !!obj;
}

function deepCopy(src) {
	let target = {};
	for (let prop in src) {
		if (src.hasOwnProperty(prop)) {
			// if the value is a nested object, recursively copy all it's properties
			if (isObject(src[prop])) {
				target[prop] = deepCopy(src[prop]);
			} else {
				target[prop] = src[prop];
			}
		}
	}
	return target;
}


function forEachFile(dir, lambda) {
	fs.readdirSync(dir, "utf8").forEach(lambda);
}

function read(filename) {
	return JSON.parse(fs.readFileSync(filename, "utf8"));
}

function requireProperty(filename, obj, prop) {
	if (obj.hasOwnProperty(prop)) return true;
	console.log("Could not read file '"+filename+"': Required property '"+prop+"' is missing.");
	return false;
}

forEachFile("./resources", element=>{
	if (!element.endsWith(".json")) {
		return;
	}
	let plan = read("./resources/"+element);

	//ensure required properties
	if (!requireProperty(element, plan, "default")) return;
	if (!requireProperty(element, plan, "generate")) return;

	let defaults = element.defaults;
	if (!requireProperty(element, defaults, "affixes")) return;

	plan.generate.forEach(resource=>{

	});

	console.log(plan);
});


/*
var templatePlan = JSON.parse(fs.readFileSync("resources.json", "utf8"));

var count = 0;
var categories = 0;

for (var property in templatePlan) {
    if (templatePlan.hasOwnProperty(property)) {
    	categories++;

        var resources = templatePlan[property];

        var templateNames = fs.readdirSync("./"+property, "utf8");

        for(i in templateNames) {
			if (!templateNames[i].endsWith(".json")) continue;

			var templateSrc = fs.readFileSync("./"+property+"/"+templateNames[i], "utf8");
			var template = handlebars.compile(templateSrc);
			console.log("generating for template "+templateNames[i]);
			for(j in resources) {
				var context = {resource: resources[j]};
				var result = template(context);
				var outName = resources[j]+(templateNames[i].substring(1));
				console.log("   generating "+outName);
				fs.writeFileSync("out/"+outName, result);
				count++;
			}
		}
	}
}

console.log("Generated "+count+" data files in "+categories+" categories.");
count = 0;

//var tagTemplatePlan = JSON.parse(fs.readFileSync("tags.json", "utf8"));
var tagTemplateSrc = fs.readFileSync("tag.json", "utf8");
var tagTemplate = handlebars.compile(tagTemplateSrc);

console.log("Generating tags for metals");
var specializations = [ "ingot", "nugget", "block", "ore", "gear", "plate", "dust" ];
for (var j in templatePlan.metals) {
	var resource = templatePlan.metals[j];
	for(i in specializations) {
		console.log("    generating "+resource+"_"+specializations[i]);
        var context = { resource: resource+"_"+specializations[i] };
        var result = tagTemplate(context);
        fs.writeFileSync("tags/"+resource+"_"+specializations[i]+".json", result);
        count++;
    }
}

console.log("Generating tags for gems");
specializations = [ "block", "ore", "gear", "plate", "dust" ];
for (var j in templatePlan.gems) {
	var resource = templatePlan.gems[j];
	console.log("    generating "+resource);
    var context = { resource: resource };
    var result = tagTemplate(context);
    fs.writeFileSync("tags/"+resource+".json", result);
    count++;

    for(var i in specializations) {
	    console.log("    generating "+resource+"_"+specializations[i]);
	    context = { resource: resource+"_"+specializations[i] }
	    result = tagTemplate(context);
	    fs.writeFileSync("tags/"+resource+"_"+specializations[i]+".json", result);
	    count++;
	}
}

console.log("Generating tags for alloys");
specializations = [ "ingot", "nugget", "block", "gear", "plate", "dust" ];
for (var j in templatePlan.alloys) {
	var resource = templatePlan.alloys[j];
	for(i in specializations) {
		console.log("    generating "+resource+"_"+specializations[i]);
        var context = { resource: resource+"_"+specializations[i] };
        var result = tagTemplate(context);
        fs.writeFileSync("tags/"+resource+"_"+specializations[i]+".json", result);
        count++;
    }
}

console.log("Generating tags for radioactive byproducts");
specializations = [ "ingot", "nugget", "block", "gear", "plate", "dust" ];
for (var j in templatePlan.radioactive) {
	var resource = templatePlan.radioactive[j];
	for(i in specializations) {
		console.log("    generating "+resource+"_"+specializations[i]);
        var context = { resource: resource+"_"+specializations[i] };
        var result = tagTemplate(context);
        fs.writeFileSync("tags/"+resource+"_"+specializations[i]+".json", result);
        count++;
    }
}

console.log("Generating unique tags");
specializations = [
	"coal_coke", "coal_coke_block",
	"iron_dust", "iron_gear", "iron_plate",
	"gold_dust", "gold_gear", "gold_plate",
	"diamond_dust", "diamond_gear", "diamond_plate"
	];

for (var j in specializations) {
	var resource = specializations[j];
	console.log("    generating "+resource);
    var context = { resource: resource };
    var result = tagTemplate(context);
    fs.writeFileSync("tags/"+resource+".json", result);
    count++;
}

console.log("Done. Generated "+count+" tags.");
*/