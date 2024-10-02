import streamlit as st
import matplotlib.pyplot as plt
import numpy as np
import tensorflow as tf
from keras import utils
import keras
from PIL import Image
from googleapiclient.discovery import build
from streamlit_player import st_player

# Replace with your YouTube Data API key
api_key = 'AIzaSyAR_YmgQT5d8mlF5poKqX9TEzylyTEYjXo'

# Build the YouTube API client
youtube = build('youtube', 'v3', developerKey=api_key)

def search_youtube_videos(keyword, max_results=2):
    request = youtube.search().list(
        q=keyword,
        part='snippet',
        type='video',
        maxResults=max_results
    )
    response = request.execute()

    # Extract video links
    video_links = []
    thumbnail_links = []
    for item in response['items']:
        video_id = item['id']['videoId']
        video_url = f"https://www.youtube.com/watch?v={video_id}"
        thumbnail_url = item['snippet']['thumbnails']['high']['url']
        video_links.append(video_url)
        thumbnail_links.append(thumbnail_url)

    return video_links, thumbnail_links

# Load the model outside the if block
model = keras.saving.load_model('C:\\Users\\PRANALI\\Projects\\Final Year\\General CNN\\trained_plant_disease_model.keras')

classes = [' Apple___Cedar_apple_rust ',
           'Apple___Apple_scab',
           ' Corn_(maize)___Common_rust_',
           'Potato___Early_blight',
           'Potato___healthy',
           'Tomato___Early_blight',
           'Tomato___Tomato_Yellow_Leaf_Curl_Virus ',
           ' Tomato___healthy ']



def preprocess_img(img):
    # op_img = Image.open(img_path)
    img2arr = utils.img_to_array(img)
    img2arr = tf.expand_dims(img2arr, 0)
    return img2arr

def predict_result(model, predict):
    pred = model.predict(predict)
    predicted_class = classes[np.argmax(pred[0])]
    confidence = round(100 * (np.max(pred[0])), 2)
    return predicted_class, confidence



st.set_page_config(
    page_title='Plant Disease Prediction',
    page_icon="🌳",
)

st.title("Plant Disease Prediction")

# Upload image
uploaded_image = st.file_uploader("Choose an image...", type=["jpg", "png", "jpeg"])

# Make prediction outside the if block
prediction = None
if uploaded_image is not None:
    # ... process uploaded image ...
    image = Image.open(uploaded_image)
    st.image(image, caption='Uploaded Image', use_column_width=True, width=100)

    # Predict the image
    st.write("Classifying...")
    image = preprocess_img(image)
    prediction, confidence = predict_result(model, image)
    
    st.header(prediction)
    st.write("Confidence: " ,confidence)
    keyword = prediction
    videos,thumbnails = search_youtube_videos(keyword)

    st.write("YouTube Videos:")
    for video in videos:
        st_player(video)

if prediction == 'Apple___Cedar_apple_rust':
    st.success("""Symptoms: Yellow-orange spots on leaves.
    Early leaf drop in severe cases. Affects both apple and cedar trees, causing damage to leaves, fruit, and twigs.""")
    st.success("""Chemical Treatment: Fungicides: Apply fungicides containing myclobutanil or propiconazole during early spring.
    Application: Start applications at bud break and repeat every 7-10 days.""")
    st.success("""Organic Treatment: Neem Oil: Neem oil sprays can help manage fungal infections.
    Garlic Spray: Apply garlic-based sprays to inhibit fungal growth.""")
    st.success("""Precautions: Remove and destroy infected leaves and plant debris. Avoid planting susceptible trees near junipers.""")
    
if prediction == 'Apple___Apple_scab':
    st.success("""Symptoms: Dark, olive-green spots on leaves and fruit.
    Premature leaf drop and fruit deformities.""")
    st.success("""Chemical Treatment: Apply fungicides like captan or sulfur during early spring.""")
    st.success("""Organic Treatment: Compost Tea: Spray compost tea to strengthen plant immunity.""")
    st.success("""Precautions: Remove infected leaves and improve air circulation around the tree.""")
    
if prediction == 'Corn_(maize)___Common_rust':
    st.success("""Symptoms: Reddish-brown pustules on leaves, often forming in clusters.
    Severely affected leaves may wither and die prematurely.""")
    st.success("""Chemical Treatment: Fungicides: Use products containing azoxystrobin or pyraclostrobin.""")
    st.success("""Organic Treatment: Neem Oil: Regular neem oil sprays can reduce rust development.""")
    st.success("""Precautions: Practice crop rotation and ensure proper plant spacing to minimize humidity.""")
    
if prediction == 'Potato___Early_blight':
    st.success("""Symptoms: Dark brown spots with concentric rings on leaves, starting from the bottom.
    Affected leaves may drop, leading to reduced yield.""")
    st.success("""Chemical Treatment: Fungicides: Apply products containing mancozeb or chlorothalonil.""")
    st.success("""Organic Treatment: Copper Spray: Use copper-based sprays to control early blight.""")
    st.success("""Precautions: Rotate crops and avoid overhead irrigation to reduce moisture on the leaves.""")
    
if prediction == 'Potato___healthy':
    st.success("""Regular Monitoring: Ensure plants receive balanced nutrients and proper irrigation to stay healthy.
    Good Agricultural Practices: Use crop rotation and avoid waterlogging to prevent diseases.""")
    
if prediction == 'Tomato___Early_blight':
    st.success("""Symptoms: Dark lesions with concentric rings on leaves, often starting from older leaves.
    Leads to early defoliation and poor fruit development.""")
    st.success("""Chemical Treatment: Use fungicides like chlorothalonil or copper-based products.""")
    st.success("""Organic Treatment: Compost Tea: Spray compost tea to strengthen plant immunity and reduce disease severity.""")
    st.success("""Precautions: Rotate crops and maintain proper plant spacing to ensure good air circulation.""")
    
if prediction == 'Tomato___Tomato_Yellow_Leaf_Curl_Virus':
    st.success("""Symptoms: Yellowing of leaves, upward curling, and stunted growth.
    Plants may produce few or no fruits.""")
    st.success("""Chemical Treatment: Insecticides: Use insecticides to control whiteflies, the vector of the virus.""")
    st.success("""Organic Treatment: Neem Oil: Neem oil sprays can help reduce whitefly populations.
    Biological Control: Introduce natural predators like ladybugs to manage whitefly infestations.""")
    st.success("""Precautions: Use virus-resistant varieties and remove infected plants to prevent the spread.""")
    
if prediction == 'Tomato___healthy':
    st.success("""Regular Monitoring: Monitor plants for pests and diseases and use preventative measures.
    Good Agricultural Practices: Maintain proper watering, fertilization, and pruning techniques.""")

# if __name__ == '__main__':
#     st.run()