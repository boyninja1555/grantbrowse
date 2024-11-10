const routes = {}

function addRoute(path, callback) {
    routes[path] = callback
}

function useRoutes() {
    const route = location.hash.slice(1) || "/"
    const callback = routes[route]
    
    if (callback) {
        callback()
    } else {
        console.log("Route not found")
    }
}

addEventListener("hashchange", useRoutes)
addEventListener("load", useRoutes)

export { addRoute }
