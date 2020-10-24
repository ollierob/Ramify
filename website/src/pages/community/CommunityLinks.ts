function communityPrefix() {
    try {
        const current = window.location.pathname;
        if (current && !current.startsWith("/community")) return "/community";
    } catch (e) {
    }
    return "";
}