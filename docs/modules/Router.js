const routes = {}

function addRoute(path, callback) {
    routes[path] = callback
}

function setErrorCallback(callback) {
    routes.error = callback
}

function useRoutes() {
    const route = location.hash.slice(1) || "/"
    const callback = routes[route]
    
    if (callback) {
        callback()
    } else {
        routes.error()
    }
}

addEventListener("hashchange", useRoutes)
addEventListener("load", useRoutes)

export { addRoute, setErrorCallback }
