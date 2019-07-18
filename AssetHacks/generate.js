var handlebars = require("handlebars");
var fs = require("fs");

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