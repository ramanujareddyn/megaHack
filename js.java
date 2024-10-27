const video = document.getElementById('video');
const poseInfo = document.getElementById('pose-info');
let detector;

async function setupCamera() {
    const stream = await navigator.mediaDevices.getUserMedia({ video: true });
    video.srcObject = stream;
    return new Promise((resolve) => {
        video.onloadedmetadata = () => {
            resolve(video);
        };
    });
}

async function loadModel() {
    detector = await poseDetection.createDetector(poseDetection.SupportedModels.PoseNet);
}

async function detectPose() {
    if (detector) {
        const poses = await detector.estimatePoses(video);
        poseInfo.textContent = JSON.stringify(poses, null, 2);
        requestAnimationFrame(detectPose);
    }
}

async function init() {
    await setupCamera();
    video.play();
    await loadModel();
    detectPose();
}

init();