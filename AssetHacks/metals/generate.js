var handlebars = require("handlebars");
var fs = require("fs");

var resources = JSON.parse(fs.readFileSync("resources.json", "utf8")).resources;
console.log("Resources: "+resources);

var templateNames = fs.readdirSync("./templates", "utf8");
for(i in templateNames) {
	if (!templateNames[i].endsWith(".json")) continue;

	var templateSrc = fs.readFileSync("templates/"+templateNames[i], "utf8");
	var template = handlebars.compile(templateSrc);
	console.log("generating for template "+templateNames[i]);
	for(j in resources) {
		var context = {resource: resources[j]};
		var result = template(context);
		var outName = resources[j]+(templateNames[i].substring(1));
		console.log("   generating "+outName);
		fs.writeFileSync("out/"+outName, result);
	}

}