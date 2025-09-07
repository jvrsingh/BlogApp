// DARK/LIGHT MODE
(function initTheme() {
  const savedTheme = localStorage.getItem("theme");
  if (savedTheme)
    document.body.classList.toggle("dark-mode", savedTheme === "dark");
})();

function toggleTheme() {
  const isDark = document.body.classList.toggle("dark-mode");
  localStorage.setItem("theme", isDark ? "dark" : "light");
}

document.addEventListener("DOMContentLoaded", () => {
  // Theme toggle button
  const btn = document.getElementById("themeToggleBtn");
  if (btn) {
    btn.textContent = document.body.classList.contains("dark-mode")
      ? "☀ Light"
      : "🌙 Dark";
    btn.onclick = () => {
      toggleTheme();
      btn.textContent = document.body.classList.contains("dark-mode")
        ? "☀ Light"
        : "🌙 Dark";
    };
  }

  // GDrive config
  const configBtn = document.getElementById("configureGDriveBtn");
  const gDriveForm = document.getElementById("gDriveConfigForm");
  const modalBackdrop = document.getElementById("modalBackdrop");
  const gDriveCancel = document.getElementById("gDriveCancelBtn");

  configBtn.onclick = () => {
    if (gDriveForm && modalBackdrop) {
      gDriveForm.style.display = "block";
      modalBackdrop.style.display = "block";
    }
  };
  gDriveCancel.onclick = () => {
    if (gDriveForm && modalBackdrop) {
      gDriveForm.style.display = "none";
      modalBackdrop.style.display = "none";
    }
  };
  gDriveForm.onsubmit = (e) => {
    e.preventDefault();
    const baseUrl = document.getElementById("gDriveBaseUrl").value;
    const fileIds = document.getElementById("gDriveFileIds").value;
    localStorage.setItem("gdriveBgBaseUrl", baseUrl);
    localStorage.setItem("gdriveFileIds", fileIds);
    gDriveForm.style.display = "none";
    modalBackdrop.style.display = "none";
    setRandomBackgroundImage();
  };

  modalBackdrop.onclick = () => {
    gDriveForm.style.display = "none";
    modalBackdrop.style.display = "none";
  };

  // SEARCH
  const searchInput = document.getElementById("searchInput");
  const suggestionList = document.getElementById("suggestionList");
  const blogList = document.getElementById("blogList");
  if (searchInput) {
    searchInput.addEventListener("keyup", filterBlogs);
    searchInput.addEventListener("input", showSuggestions);
    searchInput.addEventListener("blur", () =>
      setTimeout(() => (suggestionList.style.display = "none"), 200)
    );
  }
  document.addEventListener("click", function (event) {
    if (!suggestionList.contains(event.target) && event.target !== searchInput)
      suggestionList.style.display = "none";
  });
  function filterBlogs() {
    const filter = searchInput.value.toLowerCase();
    const lis = blogList.getElementsByTagName("li");
    for (let i = 0; i < lis.length; i++) {
      let title = lis[i].querySelector(".blog-title").textContent.toLowerCase();
      let authorSpan = lis[i].querySelector(".author");
      let author = authorSpan ? authorSpan.textContent.toLowerCase() : "";
      lis[i].style.display =
        title.includes(filter) || author.includes(filter) ? "" : "none";
    }
  }
  function showSuggestions() {
    const query = searchInput.value.trim();
    if (query.length < 2) {
      suggestionList.style.display = "none";
      suggestionList.innerHTML = "";
      return;
    }
    fetch("/api/suggest?q=" + encodeURIComponent(query))
      .then((res) => res.json())
      .then((data) => {
        suggestionList.innerHTML = "";
        if (data.length > 0) {
          data.forEach((title) => {
            const li = document.createElement("li");
            li.textContent = title;
            li.classList.add("suggestion-item");
            li.onclick = () => {
              searchInput.value = title;
              suggestionList.style.display = "none";
              filterBlogs();
            };
            suggestionList.appendChild(li);
          });
          suggestionList.style.display = "block";
        } else {
          suggestionList.style.display = "none";
        }
      })
      .catch(() => {
        suggestionList.style.display = "none";
      });
  }

  // Span coloring
  colorizeSpans();

  // Set background image (on every load/after GDrive config)
  setRandomBackgroundImage();
});

// LOCAL/SERVER IMAGES - update the array with your actual static images
const localBackgrounds = ["/img/bg1.jpg", "/img/bg2.jpg", "/img/bg3.jpg"];

// Google Drive
function chooseRandomBackground() {
  const baseUrl = localStorage.getItem("gdriveBgBaseUrl");
  const fileIds = (localStorage.getItem("gdriveFileIds") || "")
    .split(",")
    .map((x) => x.trim())
    .filter(Boolean);
  if (baseUrl && fileIds.length > 0 && Math.random() > 0.5) {
    const randId = fileIds[Math.floor(Math.random() * fileIds.length)];
    return baseUrl.replace("FILE_ID_HERE", randId);
  }
  return localBackgrounds[Math.floor(Math.random() * localBackgrounds.length)];
}
function setRandomBackgroundImage() {
  const url = chooseRandomBackground();
  document.body.style.backgroundImage = `url('${url}')`;
  document.body.style.backgroundRepeat = "no-repeat";
  document.body.style.backgroundSize = "cover";
  document.body.style.backgroundAttachment = "fixed";
  document.body.style.backgroundColor = "transparent";
  document.body.style.backgroundBlendMode = "normal";
}

// Span colorize
const pastelColors = [
  "#e0f7fa",
  "#ffe082",
  "#ffebee",
  "#f3e5f5",
  "#b2dfdb",
  "#fffde7",
];
function getRandomColor() {
  return pastelColors[Math.floor(Math.random() * pastelColors.length)];
}
function colorizeSpans() {
  document.querySelectorAll(".blog-list li > span").forEach((span) => {
    span.style.backgroundColor = getRandomColor();
    span.style.padding = "3px 9px";
    span.style.borderRadius = "8px";
    span.style.color = "#223";
    span.style.fontWeight = "600";
    span.style.marginRight = "4px";
    span.style.boxShadow = "0 1px 2px rgba(0,0,0,0.11)";
  });
}
