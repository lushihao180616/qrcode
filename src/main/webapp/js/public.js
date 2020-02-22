function navigate(url) {
    window.location.href = url;
}

function makevisible(cur, which) {
    if (which == 0)
        cur.style.opacity = 0.6;
    else
        cur.style.opacity = 1;
}