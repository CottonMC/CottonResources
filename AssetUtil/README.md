# AssetUtil

Generates resources for Cotton-Resources - but your project too, potentially.


AssetUtil looks in `./inputs/plan/` for .json or .json5 files, which it uses to plan
template lookups, and decide what and how much to generate. It looks templates up in
`./inputs/templates/`, which is split into subfolders by convention. Finally, it
generates data into `./outputs/recipes/`, `./outputs/tags/`, `./outputs/models/block`
and `item`, etc. based on the instructions in the `plan` json.


The special file `./inputs/templates/tag.json` is used for all tags.